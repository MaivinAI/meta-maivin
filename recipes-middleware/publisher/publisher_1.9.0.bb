DESCRIPTION = "EdgeFirst MCAP Publisher"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/publisher/edgefirst-publisher-${PV}-linux-${TARGET_ARCH} \
    file://LICENSE \
"
SRC_URI[sha256sum] = "007054e4f60823facc1ce3c0730a6ae56b4199a2a9226c30483e859b4e2f840c"

S = "${WORKDIR}"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-publisher-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/publisher
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
