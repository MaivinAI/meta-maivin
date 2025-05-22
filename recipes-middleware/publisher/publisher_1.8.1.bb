DESCRIPTION = "EdgeFirst MCAP Publisher"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/publisher/edgefirst-publisher-${PV}-linux-${TARGET_ARCH} \
    file://LICENSE \
"
SRC_URI[sha256sum] = "44570b866b2d4b4c5089b4fd709debec315f1b07b5eb3a32cb0263640890b9af"

S = "${WORKDIR}"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-publisher-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/publisher
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
