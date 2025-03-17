DESCRIPTION = "EdgeFirst Client CLI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/edgefirst-client/edgefirst-client-${PV}-linux-arm64 \
    file://LICENSE \
"
SRC_URI[sha256sum] = "235d6c7e45cbad4078c01c916fb206aeaff4b557acdf34efb9a4135ca9be2b8f"

S = "${WORKDIR}"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-client-${PV}-linux-arm64 ${D}${bindir}/edgefirst-client
    ln -sf edgefirst-client ${D}${bindir}/efc
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
