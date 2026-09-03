DESCRIPTION = "EdgeFirst MCAP Publisher"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/publisher/edgefirst-publisher-${PV}-linux-${TARGET_ARCH} \
    file://LICENSE \
"
SRC_URI[sha256sum] = "1d7282825401f88c683b27e5430fb84277f67fcd841d713d13051c865442138c"

S = "${WORKDIR}"

do_install:append () {
    install -d ${D}${bindir}
    if [ "${UNPACKDIR}" != "" ]; then
        install -m 0755 ${UNPACKDIR}/edgefirst-publisher-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/publisher
    else
        install -m 0755 ${WORKDIR}/edgefirst-publisher-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/publisher
    fi
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
