DESCRIPTION = "MCAP CLI"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "https://github.com/foxglove/mcap/releases/download/releases/mcap-cli/v${PV}/mcap-linux-arm64;downloadfilename=mcap"

SRC_URI[sha256sum] = "57d798a99c12ea29f2ef9d60a82ac4368a802b2c6a346ea59ff720053377243a"

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/mcap ${D}${bindir}
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
