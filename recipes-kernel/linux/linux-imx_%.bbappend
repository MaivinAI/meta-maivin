FILESEXTRAPATHS:prepend := "${THISDIR}/linux-imx:"
KBUILD_BUILD_VERSION:append = "-maivin"

SRC_URI += " file://maivin.dts file://imx8mp-maivin.dtsi file://imx8mp-wifi.dtsi "

do_unpack:append() {
    cp ${WORKDIR}/maivin.dts ${S}/arch/arm64/boot/dts/freescale/maivin.dts
    cp ${WORKDIR}/imx8mp-wifi.dtsi ${S}/arch/arm64/boot/dts/freescale/imx8mp-wifi.dtsi
    cp ${WORKDIR}/imx8mp-maivin.dtsi ${S}/arch/arm64/boot/dts/freescale/imx8mp-maivin.dtsi
}
