DESCRIPTION = "EdgeFirst Client CLI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/edgefirst-client/edgefirst-client-${PV}-linux-arm64 \
    file://LICENSE \
"
SRC_URI[sha256sum] = "b7f617e26bec699f6a0aa52a611b4ec02203caa64cbb2dc957b31acdefe5f6fb"

S = "${WORKDIR}"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-client-${PV}-linux-arm64 ${D}${bindir}/edgefirst-client
    ln -sf edgefirst-client ${D}${bindir}/dve
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
