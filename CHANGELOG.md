# Changelog — meta-maivin

All notable changes to the `meta-maivin` Yocto layer are documented here.

## [2026.10.0]

### Fixed

- `maivin-provision --sd-card` verifies the `torizon` user can write to `/media/DATA` and, with `--fix`, sets its ownership to `torizon:torizon`.
- `maivin-factory-reset` prints a reminder to run `maivin-provision --fix --sd-card` when the DATA SD card is mounted but the recorder does not store to it. The recorder default remains `$HOME/recordings` and `/etc/default` is still left untouched.
