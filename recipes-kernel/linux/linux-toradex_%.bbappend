FILESEXTRAPATHS:prepend := "${THISDIR}/linux-toradex:"
KBUILD_BUILD_VERSION:append = "-maivin"

SRC_URI:append = " file://trace.cfg"
SRC_URI:append = " file://overlay.cfg"
SRC_URI:append = " file://htu21.cfg"
SRC_URI:append = " file://xfs.cfg"
SRC_URI:append = " file://dp83tg720.cfg"
SRC_URI:append = " file://pps.cfg"

SRC_URI:append = " file://dp83tg720.c;subdir=git/drivers/net/phy"
SRC_URI:append = " file://TI-DP83TG720-PHY-for-Raivin-ETH2.patch"

SRC_URI:append = " file://imx8mp-verdin-maivin.dtsi;subdir=git/arch/arm64/boot/dts/freescale"
SRC_URI:append = " file://imx8mp-verdin-nonwifi-maivin.dts;subdir=git/arch/arm64/boot/dts/freescale"
SRC_URI:append = " file://imx8mp-verdin-wifi-maivin.dts;subdir=git/arch/arm64/boot/dts/freescale"

KERNEL_DEVICETREE:append = " freescale/imx8mp-verdin-nonwifi-maivin.dtb"
KERNEL_DEVICETREE:append = " freescale/imx8mp-verdin-wifi-maivin.dtb"

# The BSP forces CONFIG_PPS_CLIENT_GPIO=m (see pps.cfg), so load it from
# systemd-modules-load.service rather than leaving it to udev coldplug --
# chronyd starts with Restart=no and would not retry a missing /dev/pps-gps.
KERNEL_MODULE_AUTOLOAD += "pps-gpio"
