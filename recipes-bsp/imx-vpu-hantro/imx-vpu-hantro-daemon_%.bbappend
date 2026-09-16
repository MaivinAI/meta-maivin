FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://vsidaemon.service"

inherit systemd

# Appended file:// entries unpack to UNPACKDIR (Walnascar) or WORKDIR
# (Scarthgap), not ${S} -- the base recipe's S is the vendor tarball
# subdirectory.
UNPACK_ROOT = "${@d.getVar('UNPACKDIR') or d.getVar('WORKDIR')}"

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${UNPACK_ROOT}/vsidaemon.service ${D}${systemd_system_unitdir}
}

FILES:${PN} += "${systemd_system_unitdir}"

SYSTEMD_SERVICE:${PN} = "vsidaemon.service"
SYSTEMD_AUTO_ENABLE = "enable"
