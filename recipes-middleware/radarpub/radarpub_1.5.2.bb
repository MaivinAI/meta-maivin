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
SRC_URI[sha256sum] = "03f527bb8fa9c7114226aec85bd29fe5c032a1ea64f69ba24b97bdd93640963f"

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
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
