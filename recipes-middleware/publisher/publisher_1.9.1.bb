DESCRIPTION = "EdgeFirst MCAP Publisher"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/publisher/edgefirst-publisher-${PV}-linux-${TARGET_ARCH} \
    file://LICENSE \
"
SRC_URI[sha256sum] = "8be02ba2a3d37a4c1b83214b846e09da2a8e76a1a4c86198ce7109ff18aba4b3"

S = "${WORKDIR}"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-publisher-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/publisher
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
