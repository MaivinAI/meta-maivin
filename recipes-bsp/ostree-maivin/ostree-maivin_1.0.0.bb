DESCRIPTION = "Torizon for Maivin OSTree Repository"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "file://maivin.conf"

S = "${@d.getVar('UNPACKDIR') or d.getVar('WORKDIR')}"

do_install () {
    install -d ${D}${sysconfdir}/ostree/remotes.d
    sed -e 's|@URL@|${MAIVIN_OSTREE_URL}|' \
        -e 's|@BRANCH@|${MAIVIN_OSTREE_BRANCH}|' \
        ${S}/maivin.conf > ${D}${sysconfdir}/ostree/remotes.d/maivin.conf
    chmod 0644 ${D}${sysconfdir}/ostree/remotes.d/maivin.conf
}

FILES:${PN} += "${sysconfdir}"
