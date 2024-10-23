DESCRIPTION = "Maivin Sensor Fusion Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/fusion/maivin-fusion-${PV} \
    file://fusion.service \
    file://fusion.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "89ffc85364edc84ea936fb3dcae04395695aebf083c0335a1f457edca6a2671e"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/fusion.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/fusion.default ${D}${sysconfdir}/default/fusion

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-fusion-${PV} ${D}${bindir}/fusion
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "fusion.service"
SYSTEMD_AUTO_ENABLE = "disable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
