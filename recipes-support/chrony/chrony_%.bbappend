FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://systemd-time-wait-sync-chrony"
SRC_URI += "file://systemd-time-wait-sync-chrony.service"

inherit features_check systemd

do_install:append() {
    install -d ${D}${systemd_system_unitdir}

    install -m 0755 ${WORKDIR}/systemd-time-wait-sync-chrony ${D}${systemd_unitdir}
    install -m 0644 ${WORKDIR}/systemd-time-wait-sync-chrony.service ${D}${systemd_system_unitdir}
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} += "systemd-time-wait-sync-chrony.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES:${PN} += "${systemd_unitdir}"
