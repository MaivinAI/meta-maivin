DESCRIPTION = "Maivin factory-reset and provisioning check/fix tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "\
    file://maivin-factory-reset \
    file://maivin-provision \
"

S = "${@d.getVar('UNPACKDIR') or d.getVar('WORKDIR')}"

RDEPENDS:${PN} = "ostree shadow util-linux systemd"

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${S}/maivin-factory-reset ${D}${sbindir}/maivin-factory-reset
    install -m 0755 ${S}/maivin-provision ${D}${sbindir}/maivin-provision
}

FILES:${PN} = "${sbindir}/maivin-factory-reset ${sbindir}/maivin-provision"
