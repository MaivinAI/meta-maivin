FILESEXTRAPATHS:prepend := "${THISDIR}/linux-imx:"
KBUILD_BUILD_VERSION:append = "-Maivin"

SRC_URI += "file://maivin.dts"

do_configure:append() {
        cp ${WORKDIR}/maivin.dts ${S}/arch/arm64/boot/dts/freescale/maivin.dts
		cp ${WORKDIR}/imx8mp-wifi.dtsi ${S}/arch/arm64/boot/dts/freescale/imx8mp-wifi.dtsi
		cp ${WORKDIR}/imx8mp-maivin.dtsi ${S}/arch/arm64/boot/dts/freescale/imx8mp-maivin.dtsi
		cp ${WORKDIR}/imx8mp-dev.dtsi ${S}/arch/arm64/boot/dts/freescale/imx8mp-dev.dtsi
		cp ${WORKDIR}/imx8mp-dalhia.dtsi ${S}/arch/arm64/boot/dts/freescale/imx8mp-dalhia.dtsi
}