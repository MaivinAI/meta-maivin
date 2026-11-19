# Changelog — meta-maivin

All notable changes to the `meta-maivin` Yocto layer are documented here.

## [Unreleased]

### Fixed

- `vsidaemon.service` is removed, and `camera.service` and `replay.service` no longer order themselves after it. The VPU driver starts `vsidaemon` itself, and a second daemon started by systemd raced it for the driver's control pipe, which hung VPU clients and the ISP on some boots and crashed `vsidaemon`.
- The VPU driver admits a single `vsidaemon`, waits at most 3 s for it to exit on the last close instead of blocking forever, and does not start another daemon while one is connected.
- The MIPI CSI-2 receiver enables its error interrupts on the first frame start instead of at stream on, and masks them until the next frame start when more than 64 arrive without one, so a receiver left without a valid stream can no longer flood the CPU with interrupts.
- `kernel-module-isp-vvcam` builds the Au-Zone fork rebased on NXP `lf-6.6.y_2.2.0`, the release NXP pairs with isp-imx 4.2.2.24.4. It holds the event lock while each ISP event is filled and posted, so concurrent instances no longer corrupt each other's events, and serialises format and buffer setup. The OS08A20 driver matches both `ovti,os08a20_mipi` and `ovti,os08a20`.
- `imx8-isp.service` hides the Hantro VPU decoder and encoder nodes from `isp_media_server` (`InaccessiblePaths=`), so the ISP's scan of every `/dev/video*` no longer starts and stops `vsidaemon` at boot. The nodes are selected by their `vpu_v4l2` platform device through the `/dev/v4l/by-path` links. A udev rule gives those links systemd device units, the ISP starts after them, and each wait is capped at 10 s. Other VPU clients, including the camera's H.264 encoder, still see the nodes.

## [2026.10.0]

### Fixed

- `maivin-provision --sd-card` verifies the `torizon` user can write to `/media/DATA` and, with `--fix`, sets its ownership to `torizon:torizon`.
- `maivin-factory-reset` prints a reminder to run `maivin-provision --fix --sd-card` when the DATA SD card is mounted but the recorder does not store to it. The recorder default remains `$HOME/recordings` and `/etc/default` is still left untouched.
