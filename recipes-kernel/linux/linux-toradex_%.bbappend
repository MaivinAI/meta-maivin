FILESEXTRAPATHS:prepend := "${THISDIR}/linux-toradex:"
KBUILD_BUILD_VERSION:append = "-maivin"

SRC_URI:append = " file://imx8mp-verdin-maivin.dtsi"
SRC_URI:append = " file://imx8mp-verdin-nonwifi-maivin.dts"
SRC_URI:append = " file://imx8mp-verdin-wifi-maivin.dts"

KERNEL_DEVICETREE:append = " freescale/imx8mp-verdin-nonwifi-maivin.dtb"
KERNEL_DEVICETREE:append = " freescale/imx8mp-verdin-wifi-maivin.dtb"

do_configure:prepend() {
    cp ${WORKDIR}/imx8mp-verdin-maivin.dtsi ${S}/arch/arm64/boot/dts/freescale/imx8mp-verdin-maivin.dtsi
    cp ${WORKDIR}/imx8mp-verdin-nonwifi-maivin.dts ${S}/arch/arm64/boot/dts/freescale/imx8mp-verdin-nonwifi-maivin.dts
    cp ${WORKDIR}/imx8mp-verdin-wifi-maivin.dts ${S}/arch/arm64/boot/dts/freescale/imx8mp-verdin-wifi-maivin.dts
}
