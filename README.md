# Torizon for Maivin

Torizon for Maivin is a customized [Torizon OS](https://www.toradex.com/torizon) 7 distribution for the **Maivin AI Vision platform** — an edge AI starter kit by [Au-Zone Technologies](https://au-zone.com) built on the Toradex Verdin iMX8M Plus SoM mounted on a custom Maivin carrier board.

Unlike typical Torizon deployments where applications run in Docker containers, Maivin's perception services run **natively on the host OS** as systemd services. This architecture provides direct hardware access to the ISP, NPU, VPU, and sensor interfaces without container overhead — critical for real-time perception workloads on the i.MX 8M Plus. The kernel uses PREEMPT-RT for deterministic timing.

The platform delivers a working perception system out of the box: camera capture, AI inference, and a web UI are pre-configured and running at first boot. Additional capabilities — radar fusion, LiDAR, data recording — are pre-installed but disabled until activated during device provisioning. The standard Torizon container engine (Docker) remains available for user application containers.

## Layer Architecture

The Maivin platform is composed of three additional Yocto layers on top of the standard Torizon/Toradex BSP:

### meta-maivin (priority 90)

The top-level integration layer. Provides:

- **Distro configuration** (`torizon-maivin`) extending Torizon with PREEMPT-RT and Maivin-specific defaults
- **Image recipe** (`torizon-core-maivin`) assembling the complete system
- **Service customization** via bbappends that rename and configure EdgeFirst services for the Maivin platform
- **Network configuration**: NetworkManager profiles, systemd-networkd masking, WiFi AP, CAN bus, automotive Ethernet
- **Kernel customization**: Maivin device trees, DP83TG720 automotive Ethernet PHY driver, config fragments
- **Device tree overlays**: 10 overlays for Maivin 1 and Maivin 2 hardware variants
- **Time synchronization**: PTP grandmaster, phc2sys, chrony with GPSD refclock

### meta-edgefirst (priority 10)

Generic EdgeFirst perception service recipes. Downloads pre-built Rust binaries from GitHub Releases for each service (camera, model, fusion, etc.). Also provides:

- **EdgeFirst HAL** and **Schemas** libraries
- **VideoStream** — zero-copy video frame sharing (Apache-2.0)
- **Zenoh** messaging daemon and Python bindings
- **MCAP** recording library
- **EdgeFirst client** CLI and Python bindings

All base recipes set `SYSTEMD_AUTO_ENABLE = "disable"` — meta-maivin's bbappends selectively enable services and customize their systemd units for the Maivin platform.

`tensorflow-lite-vx-delegate` (TensorFlow Lite delegate for the NXP VeriSilicon NPU) is a meta-maivin recipe (`recipes-visionpack/tensorflow-lite/`), not meta-edgefirst's.

### meta-kinara (priority 10)

Kinara Ara-2 NPU accelerator runtime (proprietary, NDA-licensed):

| Recipe | Description |
|--------|-------------|
| `ara2` | Proxy daemon, firmware, client libraries, hw_utils, Python bindings |
| `kernel-module-uiodma` | UIO DMA kernel module for Ara-2 PCIe communication |

Requires `KINARA_MIRROR` set in `local.conf` to access the NDA-licensed download server. The Ara-2 is an external PCIe accelerator card installed in the Maivin carrier board's M.2 slot.

## EdgeFirst Perception Services

### Customization Pattern

Each EdgeFirst service follows a two-layer recipe strategy:

1. **meta-edgefirst** provides the base recipe that downloads a pre-built binary from GitHub Releases as `edgefirst-<service>`, installs a default systemd unit as `edgefirst-<service>.service`, and a default config as `/etc/default/edgefirst-<service>`. Auto-start is disabled.

2. **meta-maivin** provides a bbappend that:
   - Creates a **binary symlink**: `/usr/bin/<service>` → `/usr/bin/edgefirst-<service>`
   - **Renames the config** file: `/etc/default/edgefirst-<service>` → `/etc/default/<service>`
   - **Replaces the systemd unit** with a Maivin-specific version that defines proper ordering, dependencies, and target membership
   - Sets **auto-start** policy (enabled or disabled)

### Service Inventory

| Short Name | Auto-Start | Dependencies | Description |
|------------|-----------|--------------|-------------|
| `camera` | enabled | imx8-isp | Camera capture via ISP pipeline |
| `model` | enabled | camera | AI model inference (TFLite people detection) |
| `fusion` | disabled | model; radarpub, lidarpub (ordering only) | Radar-camera sensor fusion |
| `imu` | enabled | — | IMU sensor data |
| `navsat` | enabled | gpsd | GPS/GNSS navigation |
| `radarpub` | disabled | — | Automotive radar data publisher |
| `lidarpub` | disabled | — | LiDAR point cloud publisher |
| `websrv` | enabled | — | Web server (Go HTTP backend, serves edgefirst-webui static files) |
| `recorder` | disabled | — | MCAP data recording (runs as `torizon` user) |
| `replay` | disabled | — | MCAP data playback |

Services enabled by default (camera, model, imu, navsat, websrv) constitute the base Maivin perception stack. Disabled services (fusion, radarpub, lidarpub, recorder, replay) are for automotive radar configurations or data capture workflows and are activated during device provisioning.

### Supporting Packages

In addition to the services above, the image includes:

- **edgefirst-webui** — static HTML/CSS/JavaScript frontend, installed to `/usr/share/edgefirst/webui/` and served by the `websrv` service
- **edgefirst-client** — Python SDK and CLI tool for interacting with Maivin services
- **publisher** — MCAP data publisher CLI utility
- **edgefirst-schemas** — data schema definitions (with Python bindings)
- **zenohd** — Zenoh messaging daemon used for inter-service communication
- **mcap** — MCAP recording format library

### Pre-loaded Models

The **model** bbappend ships three pre-trained TFLite people detection models installed to `/usr/share/edgefirst/model/`:

- `modelpack-people.tflite` — multi-task (detection + classification)
- `modelpack-people-mask.tflite` — segmentation
- `modelpack-people-detect.tflite` — detection-only

The **fusion** bbappend ships three RadarExp radar-camera fusion models installed to `/usr/share/edgefirst/fusion/`.

## System Targets

### maivin.target

The middleware anchor target. All perception services use `WantedBy=maivin.target` to start together after `multi-user.target` is reached.

```ini
[Unit]
Description=Maivin Middleware Services
After=multi-user.target
AllowIsolate=yes
Requires=multi-user.target
```

### raivin.target

A backward-compatibility symlink to `maivin.target`. Historically, "Raivin" referred to the automotive radar configuration. The symlink ensures existing references to `raivin.target` continue to work.

### Service Dependency Chain

```
maivin.target (After: multi-user.target)
  ├─ imu.service         (enabled, Before: recorder)
  ├─ navsat.service       (enabled, After: gpsd, Before: recorder)
  ├─ camera.service       (enabled, After: imx8-isp, Before: recorder)
  │   └─ model.service    (enabled, After: camera, Before: recorder)
  │       └─ fusion.service (disabled, After: model + radarpub + lidarpub)
  ├─ websrv.service       (enabled)
  ├─ radarpub.service     (disabled, Before: recorder)
  ├─ lidarpub.service     (disabled, Before: recorder)
  ├─ recorder.service     (disabled, ordered after sensors via their Before= directives)
  └─ replay.service       (disabled)
```

### maivin meta-package

The `maivin` recipe (`maivin_1.1.bb`) is a meta-package that pulls in runtime dependencies and installs platform configuration:

- **Runtime dependencies**: edgefirst-imu, edgefirst-navsat, edgefirst-camera, edgefirst-model, edgefirst-webui, edgefirst-radarpub, edgefirst-fusion
- **Installed units**: maivin.target, raivin.target (symlink), ethernet1-master.service, can0.service
- **Network profiles**: ethernet1-radar.nmconnection, ethernet1-lidar.nmconnection
- **systemd-networkd masking**: symlinks service, socket, and wait-online to `/dev/null`

## Network Management

### NetworkManager as Sole Manager

Maivin uses **NetworkManager exclusively** for all Ethernet and WiFi management. systemd-networkd is fully masked (service, socket, and wait-online symlinked to `/dev/null`) to prevent conflicts.

CAN interfaces are the exception — NetworkManager does not support CAN, so `can0.service` uses `ip link` commands directly.

### Ethernet Profiles

Three NetworkManager connection profiles are installed:

#### network0 — External Network

Installed by the NetworkManager bbappend. Connection profile `network0` binds to `ethernet0` (standard Gigabit Ethernet) via `interface-name`. DHCP, autoconnect enabled.

#### ethernet1-radar — Sensor Network (default)

Installed by the maivin meta-package. Static IP `192.168.11.17/24` on `ethernet1`, autoconnect enabled. Used for communication with automotive radar sensors.

```ini
[ipv4]
method=manual
address1=192.168.11.17/24
route-table=100
never-default=true
routing-rule1=priority 100 to 192.168.11.0/24 table 100
```

#### ethernet1-lidar — Sensor Network (alternate)

Installed by the maivin meta-package. Static IP `192.168.1.102/24` on `ethernet1`, autoconnect disabled. Used for communication with LiDAR sensors.

```ini
[ipv4]
method=manual
address1=192.168.1.102/24
route-table=100
never-default=true
routing-rule1=priority 100 to 192.168.1.0/24 table 100
```

### Policy Routing

Both sensor network profiles use **policy routing** to isolate sensor traffic from the default route:

- `route-table=100` — all routes for the sensor subnet go into routing table 100
- `never-default=true` — sensor interface never becomes the default gateway
- `routing-rule1=priority 100 to <subnet> table 100` — traffic destined for the sensor subnet uses table 100

This ensures that sensor traffic on `ethernet1` never interferes with internet-bound traffic on `ethernet0`.

### Profile Switching

During provisioning, switch between radar and lidar profiles using `nmcli`:

```shell
# Switch to lidar profile
nmcli connection down ethernet1-radar
nmcli connection up ethernet1-lidar

# Switch back to radar profile
nmcli connection down ethernet1-lidar
nmcli connection up ethernet1-radar
```

### WiFi Access Point

Maivin provides a built-in WiFi access point using hostapd on the `uap0` interface (Marvell WiFi virtual AP interface):

| Setting | Value |
|---------|-------|
| SSID | `Maivin` |
| Interface | `uap0` |
| Band | 5 GHz (802.11ac) |
| Channel | 40 |
| Security | WPA2-PSK |
| AP IP | `10.10.10.1/24` |
| DHCP range | `10.10.10.100` – `10.10.10.199` |

The `uap0` interface is excluded from NetworkManager via `unmanaged.conf`:

```ini
[keyfile]
unmanaged-devices=interface-name:uap*
```

The AP stack consists of two systemd services:

- **hostapd.service** — starts hostapd, bound to the `uap0` device lifecycle
- **hostapd-network.service** — configures the AP IP address, starts a dedicated dnsmasq instance for DHCP, enables IP forwarding, and sets up NAT masquerading so AP clients can reach the internet via the Maivin's uplink

## CAN Bus

CAN is configured by `can0.service` (installed by the maivin meta-package):

```ini
[Unit]
Description=CAN Bus Setup
BindsTo=sys-subsystem-net-devices-can0.device
After=sys-subsystem-net-devices-can0.device

[Service]
Type=oneshot
RemainAfterExit=yes
ExecStart=/sbin/ip link set can0 type can bitrate 500000
ExecStart=/sbin/ip link set can0 up
ExecStop=/sbin/ip link set can0 down
```

The service binds to the CAN device lifecycle — it only runs when the CAN hardware is present. NetworkManager does not support CAN interfaces, so direct `ip link` commands are used.

## Automotive Ethernet

The Maivin carrier board supports a DP83TG720 1000Base-T1 automotive Ethernet PHY on `ethernet1`. In point-to-point automotive Ethernet, one end must be the PHY master.

`ethernet1-master.service` forces the Maivin side to master role:

```ini
[Unit]
Description=Configures ethernet1 as the automotive ethernet master
BindsTo=sys-subsystem-net-devices-ethernet1.device
After=sys-subsystem-net-devices-ethernet1.device network.target
Before=radarpub.service

[Service]
Type=oneshot
RemainAfterExit=yes
ExecStart=/usr/sbin/ethtool -s ethernet1 master-slave forced-master
Restart=on-failure
```

The service runs before `radarpub.service` to ensure the PHY link is established before the radar publisher attempts communication.

The DP83TG720 kernel driver is injected by the kernel bbappend (`dp83tg720.c` source + `dp83tg720.cfg` config fragment setting `CONFIG_DP83TG720_PHY=y`).

## Time Synchronization

Maivin implements a multi-layer time synchronization stack for microsecond-accurate timestamps across all sensor data.

### PTP Grandmaster (ptp4l)

PTP4L runs as a PTP grandmaster on `ethernet1`, distributing time to connected radar and LiDAR sensors. The service binds to the `ethernet1` device and starts after `ethernet1-master.service`.

Configuration (`/etc/ptp4l.conf`) uses hardware timestamping with the default PTP profile.

### PHC-to-System Clock (phc2sys)

Two templated phc2sys services are provided:

- **phc2sys-master@ethernet1.service** (enabled by default) — syncs **from** the system clock (NTP/GPSD-disciplined) **to** the `ethernet1` PHC. This makes the Maivin the PTP time source for sensors. Uses 16 Hz update rate with step threshold for boot-time convergence.
- **phc2sys@.service** — syncs from a PHC to the system clock (slave mode, for when an external PTP master is present).

### GNSS 1 PPS

The GNSS receiver's TIMEPULSE output reaches the SoM on **Verdin SODIMM 64** (QSPI_1_CS2#), which the i.MX8MP brings out as **GPIO3_IO16**. `maivin2.dts` declares a `pps-gpio` node on that line, and `pps.cfg` builds `CONFIG_PPS_CLIENT_GPIO` into the kernel.

The pulse idles low and asserts high for 100 ms once per second, so the rising edge marks the top of the second — `assert-falling-edge` must **not** be set.

> **PPS cannot come from the UART on this board.** The GNSS receiver is on `uart4` (`/dev/ttymxc3`), whose pinmux carries RXD and TXD only. `pps-ldisc` captures pulses on the UART's DCD modem-status line, and this port has no DCD pin. If gpsd attaches the PPS line discipline anyway you will see a `IMX-uart3` entry in `/sys/class/pps/` that registers correctly and captures zero edges, forever.

The `pps` class enumerates in probe order, so `pps-gpio`, the ethernet PHC and the line discipline swap numbers between boots. A udev rule in `maivin.rules` provides the stable name **`/dev/pps-gps`**; always refer to that, never `/dev/ppsN`.

### Chrony with GNSS Refclocks

Chrony is configured through a drop-in (`/etc/chrony/conf.d/gpsd.conf`):

```
refclock SHM 0 refid NMEA delay 0.5 poll 0 noselect
refclock PPS /dev/pps-gps refid GPS lock NMEA maxlockage 10 precision 1e-7 poll 0 prefer
```

- **SHM 0 (NMEA)**: GPS time from gpsd via NMEA sentences (~200 ms accuracy), marked `noselect` — it exists only to tell the PPS refclock *which second* each pulse belongs to
- **PPS (GPS)**: the kernel `pps-gpio` capture, read directly by chrony rather than routed through gpsd's SHM 1. A bare pulse cannot name its own second, hence `lock NMEA`; `maxlockage 10` keeps pulses usable when an NMEA sample is briefly late

The stock `chrony.conf` ships **no `include` directive**, so the `chrony_%.bbappend` adds one and removes the inline `refclock SHM 0` that the drop-in supersedes. Without the include the drop-in is silently ignored and chrony falls back to pool NTP.

A drop-in override on upstream's `systemd-time-wait-sync.service` (`10-chrony-waitsync.conf`) runs a bounded `chronyc waitsync` wait so dependent services (e.g. `auto-provisioning.service`) start once the clock is synchronized -- or, on a unit with neither a GPS fix nor network NTP reachable, once the wait times out, so boot is never blocked indefinitely.

Verify on target with `chronyc sources` — a healthy system shows `#* GPS` selected at stratum 1:

```
MS Name/IP address         Stratum Poll Reach LastRx Last sample
#* GPS                          0   0   377     0    -32us[ -37us] +/-  125ns
```

### Time Flow

```
GNSS receiver
  ├─→ NMEA over /dev/ttymxc3 → GPSD → SHM 0 ─┐
  └─→ TIMEPULSE on SODIMM 64 → pps-gpio ─────┤
                                             └─→ Chrony (disciplines system clock)
                                                  └─→ phc2sys-master (system clock → ethernet1 PHC)
                                                       └─→ ptp4l (PHC time to sensors via PTP)
```

## AI/ML Stack

### On-chip NPU (NXP VeriSilicon, 2.25 TOPS)

The primary inference accelerator. Used via:

- **TensorFlow Lite** with the **VX delegate** (`tensorflow-lite-vx-delegate`) — maps TFLite ops to the NPU
- **TIM-VX** / **nn-imx** — NXP's neural network libraries (underlying VX delegate)

Pre-trained people detection models ship with the `edgefirst-model` service.

### External PCIe NPU (Kinara Ara-2)

Optional external accelerator installed in the M.2 PCIe slot. Provides additional inference capacity via the Ara-2 SDK. NDA-licensed — requires `KINARA_MIRROR` in `local.conf`.

### VPU (Video Processing Unit)

Hardware H.264/H.265 encode and decode via `imx-vpu-hantro` (decoder) and `imx-vpu-hantro-vc` (encoder). Used by the camera pipeline and NNStreamer.

### ISP (Image Signal Processor)

The `imx8-isp` service provides the camera ISP pipeline for the OS08A20 4K sensor (Maivin 2 default). The camera service depends on `imx8-isp.service` being started first.

## Device Tree Overlays

The `maivin-overlays` recipe fetches device tree overlays from `github.com/MaivinAI/maivin-overlays`. Ten overlays cover both hardware generations:

### Maivin 1

| Overlay | Purpose |
|---------|---------|
| `maivin1.dtbo` | Base hardware configuration |
| `maivin1-ar0521.dtbo` | AR0521 camera sensor (e-ConSystems CSI module) |
| `maivin1-os08a20.dtbo` | OS08A20 camera sensor (via ISP) |
| `maivin1-m2pcie.dtbo` | M.2 slot in PCIe mode (for Ara-2 NPU) |
| `maivin1-m2usb.dtbo` | M.2 slot in USB mode |
| `maivin1-gps.dtbo` | GPS module |

### Maivin 2

| Overlay | Purpose |
|---------|---------|
| `maivin2.dtbo` | Base hardware configuration |
| `maivin2-os08a20.dtbo` | OS08A20 camera sensor (via ISP) |
| `maivin2-m2pcie.dtbo` | M.2 slot in PCIe mode (for Ara-2 NPU) |
| `maivin2-m2usb.dtbo` | M.2 slot in USB mode |

Overlays are pre-configured in `/etc/overlays.txt` during device provisioning. The `update-overlays` service synchronizes this configuration to the boot partition and triggers a reboot if the overlay list has changed.

## Build Instructions

### Prerequisites

- Linux build host with [Yocto prerequisites](https://docs.yoctoproject.org/ref-manual/system-requirements.html) installed
- Google [`repo`](https://gerrit.googlesource.com/git-repo/) tool
- SSH keys configured for GitHub (MaivinAI, EdgeFirstAI organizations)

### Setup

```shell
mkdir torizon-maivin && cd torizon-maivin
repo init -u ssh://git@bitbucket.org/au-zone/torizon-maivin -b main -m maivin/default.xml
repo sync
MACHINE=verdin-imx8mp source setup-maivin build
```

The `setup-maivin` script sources the upstream Toradex build environment and copies the Maivin `bblayers.conf` (which adds meta-maivin, meta-edgefirst, and meta-kinara to the layer list).

### Build

```shell
bitbake torizon-core-maivin
```

### Developer site.conf

Create `build/conf/site.conf` to set local paths:

```bitbake
SCONF_VERSION = "1"
DL_DIR ?= "/path/to/downloads"
SSTATE_DIR ?= "/path/to/sstate-cache"
TMPDIR = "/path/to/tmp"
```
