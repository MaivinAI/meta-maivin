DESCRIPTION = "Maivin MCAP Publisher"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/publisher/maivin-publisher-${PV}-linux-arm64 \
    file://LICENSE \
"
SRC_URI[sha256sum] = "9011c1e59d32ba5bc04066e39a3a730f409295f4fae8c2dbabc17f5ac16704ef"

S = "${WORKDIR}"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-publisher-${PV}-linux-arm64 ${D}${bindir}/publisher
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
