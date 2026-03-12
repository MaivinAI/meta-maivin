FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " file://ptp4l.service"
SRC_URI += " file://phc2sys@.service"
SRC_URI += " file://phc2sys-master@.service"
SRC_URI += " file://ptp4l.conf"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/ptp4l.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/phc2sys@.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/phc2sys-master@.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/ptp4l.conf ${D}${sysconfdir}

    # Enable ptp4l and phc2sys-master on ethernet1 by default
    install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
    ln -sf ${systemd_system_unitdir}/ptp4l.service \
        ${D}${sysconfdir}/systemd/system/multi-user.target.wants/ptp4l.service
    ln -sf ${systemd_system_unitdir}/phc2sys-master@.service \
        ${D}${sysconfdir}/systemd/system/multi-user.target.wants/phc2sys-master@ethernet1.service
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "ptp4l.service phc2sys@.service phc2sys-master@.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
CONFFILES:${PN} += "${sysconfdir}"
