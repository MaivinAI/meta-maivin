DESCRIPTION = "Seed /etc defaults that OSTree may not merge onto existing units"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "file://maivin-etc-defaults.conf"

S = "${@d.getVar('UNPACKDIR') or d.getVar('WORKDIR')}"

do_install() {
    install -d ${D}${nonarch_libdir}/tmpfiles.d
    install -m 0644 ${S}/maivin-etc-defaults.conf \
        ${D}${nonarch_libdir}/tmpfiles.d/maivin-etc-defaults.conf
}

FILES:${PN} = "${nonarch_libdir}/tmpfiles.d/maivin-etc-defaults.conf"
