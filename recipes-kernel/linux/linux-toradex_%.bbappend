FILESEXTRAPATHS:prepend := "${THISDIR}/linux-toradex:"
KBUILD_BUILD_VERSION:append = "-maivin"

SRC_URI:append = " file://overlay.cfg"
SRC_URI:append = " file://htu21.cfg"

SRC_URI:append = " file://imx8mp-verdin-maivin.dtsi;subdir=git/arch/arm64/boot/dts/freescale"
SRC_URI:append = " file://imx8mp-verdin-nonwifi-maivin.dts;subdir=git/arch/arm64/boot/dts/freescale"
SRC_URI:append = " file://imx8mp-verdin-wifi-maivin.dts;subdir=git/arch/arm64/boot/dts/freescale"

KERNEL_DEVICETREE:append = " freescale/imx8mp-verdin-nonwifi-maivin.dtb"
KERNEL_DEVICETREE:append = " freescale/imx8mp-verdin-wifi-maivin.dtb"
