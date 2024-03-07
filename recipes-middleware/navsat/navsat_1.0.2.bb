DESCRIPTION = "Maivin NavSat Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/navsat/maivin-navsat-${PV} \
    file://navsat.service \
    file://LICENSE \
"
SRC_URI[sha256sum] = "4f64627fee5efe7e861e8e4555e7d9e1bb71bc91bfd95ba8bc3a1cade80851d0"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/navsat.service ${D}${systemd_system_unitdir}

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-navsat-${PV} ${D}${bindir}/navsat
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "navsat.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${bindir}"
