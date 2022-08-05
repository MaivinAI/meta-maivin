FILESEXTRAPATHS:prepend := "${THISDIR}/linux-imx:"
KBUILD_BUILD_VERSION:append = "-maivin"

SRC_URI += " file://maivin.dts file://imx8mp-maivin.dtsi "

do_configure:prepend() {
    cp ${WORKDIR}/maivin.dts ${S}/arch/arm64/boot/dts/freescale/maivin.dts
    cp ${WORKDIR}/imx8mp-maivin.dtsi ${S}/arch/arm64/boot/dts/freescale/imx8mp-maivin.dtsi
}
