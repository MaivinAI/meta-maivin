FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " file://ptp4l.service"
SRC_URI += " file://phc2sys@.service"
SRC_URI += " file://ptp4l.conf"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/ptp4l.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/phc2sys@.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/ptp4l.conf ${D}${sysconfdir}
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "ptp4l.service phc2sys@.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"

FILES:${PN} += "${systemd_system_unitdir}"
CONFFILES:${PN} += "${sysconfdir}"