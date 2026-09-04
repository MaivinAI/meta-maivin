# Maivin and Raivin are headless: no panel, no console on the framebuffer,
# nothing to draw a boot splash on. Everything the splash machinery brings
# into the initramfs is dead weight, and one piece of it is worse than that.
#
# initramfs-module-plymouth installs /init.d/02-plymouth, which starts
# plymouthd from the initramfs. Nothing ever stops it: the plymouth package
# is not installed in the Torizon rootfs, so the plymouth-quit and
# plymouth-quit-wait units that would normally tell it to exit do not exist
# there -- systemd lists all three plymouth units as "not-found". The daemon
# survives switch-root with its executable no longer present in the new
# root, reparents to PID 1, keeps /dev/tty1 open, and spins forever.
# Measured on verdin-imx8mp-15141015 running build #80: 102 CPU ticks per
# 30s, a sustained 3.4% of one core, from boot until power-off, drawing an
# animation for a display that does not exist. EDGEAI-1444.
#
# Dropping the module rather than passing "nosplash" on the kernel command
# line -- the switch 02-plymouth itself honours -- means the plymouth binary
# and theme never enter the initramfs at all, so there is nothing left to
# start by accident and the image gets smaller. The splash IMAGE_FEATURE
# goes with it: it pulls in psplash (SPLASH resolves to psplash when
# MACHINE_FEATURES has "screen"), which is the same idea by another route.
#
# INITRAMFS_EXTRA_KMODS is deliberately left alone. Those LVDS/DSI/bridge
# modules exist only to light a panel for the splash, so they are equally
# useless here, but they are loaded by initramfs-module-kmod, which is doing
# other work we do need -- that is a separate change with its own risk.
PACKAGE_INSTALL:remove = "initramfs-module-plymouth"
IMAGE_FEATURES:remove = "splash"
