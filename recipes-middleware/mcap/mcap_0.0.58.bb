DESCRIPTION = "MCAP CLI"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "https://github.com/foxglove/mcap/releases/download/releases/mcap-cli/v${PV}/mcap-linux-arm64;downloadfilename=mcap"
SRC_URI[sha256sum] = "6270421b1db5db5e012c1c0e8fcd8ed13667715722640b1ded63a3d177c39b9b"

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/mcap ${D}${bindir}
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
