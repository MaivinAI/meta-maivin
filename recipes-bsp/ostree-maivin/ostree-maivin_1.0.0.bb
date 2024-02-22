DESCRIPTION = "Torizon for Maivin OSTree Repository"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://maivin.conf;md5=e8d1a16c9b266819b616f31c48c356dc"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "file://maivin.conf"

S = "${WORKDIR}"

do_install () {
    install -d ${D}/${sysconfdir}/ostree/remotes.d
    install -m 0644 ${S}/maivin.conf ${D}/${sysconfdir}/ostree/remotes.d/
}

FILES:${PN} += "${sysconfdir}"
