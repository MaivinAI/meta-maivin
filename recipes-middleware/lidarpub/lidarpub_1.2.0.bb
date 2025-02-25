DESCRIPTION = "Maivin LiDAR Publisher"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/lidarpub/maivin-lidarpub-${PV} \
    file://lidarpub.service \
    file://lidarpub.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "3b825a174771b978fbbd4e03c216338b6cdd80350c1c09dc9990eaab5d5dfa85"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/lidarpub.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/lidarpub.default ${D}${sysconfdir}/default/lidarpub

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-lidarpub-${PV} ${D}${bindir}/lidarpub
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "lidarpub.service"
SYSTEMD_AUTO_ENABLE = "disable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
