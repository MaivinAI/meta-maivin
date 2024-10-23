DESCRIPTION = "Maivin Radar Publisher"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/radarpub/maivin-radarpub-${PV} \
    file://radarpub.service \
    file://radarpub.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "840422fa32fcd3000ca07c4911f0bc6872a4422027bbae0d723afd591a33acc6"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/radarpub.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/radarpub.default ${D}${sysconfdir}/default/radarpub

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-radarpub-${PV} ${D}${bindir}/radarpub
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "radarpub.service"
SYSTEMD_AUTO_ENABLE = "disable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
