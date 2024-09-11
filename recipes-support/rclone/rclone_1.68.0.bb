DESCRIPTION = "Rclone is a command-line program to manage files on cloud storage"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "https://downloads.rclone.org/v${PV}/rclone-v${PV}-linux-arm64.zip"
SRC_URI[sha256sum] = "1ff887245f07ce722b9245c9aa984a841b6ba47d719842b1ada6be85e08695dc"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/rclone-v${PV}-linux-arm64/rclone ${D}${bindir}
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
