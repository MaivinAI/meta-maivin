# Changelog — meta-maivin

All notable changes to the `meta-maivin` Yocto layer are documented here.

## [Unreleased]

### Fixed

- `imx8-isp.service` hides the Hantro VPU decoder and encoder nodes from `isp_media_server` (`InaccessiblePaths=`), so a request `vsidaemon` loses can no longer block the ISP and leave the camera without frames for the whole boot. The nodes are selected by their `vpu_v4l2` platform device through the `/dev/v4l/by-path` links, not by `/dev/videoN` number. A udev rule gives those links systemd device units, the ISP starts after them, and each wait is capped at 10 s. `vsidaemon` and other VPU clients are unaffected.

## [2026.10.0]

### Fixed

- `maivin-provision --sd-card` verifies the `torizon` user can write to `/media/DATA` and, with `--fix`, sets its ownership to `torizon:torizon`.
- `maivin-factory-reset` prints a reminder to run `maivin-provision --fix --sd-card` when the DATA SD card is mounted but the recorder does not store to it. The recorder default remains `$HOME/recordings` and `/etc/default` is still left untouched.
