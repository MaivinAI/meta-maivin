FILESEXTRAPATHS:prepend := "${THISDIR}/linux-toradex:"
KBUILD_BUILD_VERSION:append = "-maivin"

SRC_URI += "\
    file://imx8mp-verdin-maivin.dtsi \
    file://imx8mp-verdin-nonwifi-maivin.dts \
    file://imx8mp-verdin-wifi-maivin.dts\
"

do_configure:prepend() {
    cp ${WORKDIR}/imx8mp-verdin-maivin.dtsi ${S}/arch/arm64/boot/dts/freescale/imx8mp-verdin-maivin.dtsi
    cp ${WORKDIR}/imx8mp-verdin-nonwifi-maivin.dts ${S}/arch/arm64/boot/dts/freescale/imx8mp-verdin-nonwifi-maivin.dts
    cp ${WORKDIR}/imx8mp-verdin-wifi-maivin.dts ${S}/arch/arm64/boot/dts/freescale/imx8mp-verdin-wifi-maivin.dts
}
