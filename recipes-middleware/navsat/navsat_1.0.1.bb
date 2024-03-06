DESCRIPTION = "Maivin NavSat Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/navsat/maivin-navsat-${PV} \
    file://navsat.service \
    file://LICENSE \
"
SRC_URI[sha256sum] = "f5ce164262b8747af84797db2156d6206acc785e639e6d1ffc2310f3a720ea23"

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
