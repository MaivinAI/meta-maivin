DESCRIPTION = "Maivin MCAP Publisher"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/publisher/maivin-publisher-${PV}-linux-arm64 \
    file://LICENSE \
"
SRC_URI[sha256sum] = "e862474a436db0c229cc9850b7d579ffa34b748aed73f83c0b4cad7ba9528cca"

S = "${WORKDIR}"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-publisher-${PV}-linux-arm64 ${D}${bindir}/publisher
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
