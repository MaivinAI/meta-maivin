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
SRC_URI[sha256sum] = "b56ad63ceb7ba7e13b109d06b28dde2dc21c9c87892a61ee561cd5723bc66e0e"

DEPENDS = "vaal deepview-rt"
RDEPENDS_${PN} = "libvaal libdeepview-rt"

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
