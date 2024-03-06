DESCRIPTION = "Maivin IMU Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/imu/maivin-imu-${PV} \
    file://imu.service \
    file://LICENSE \
"
SRC_URI[sha256sum] = "2cc59748258f9a4918bb1ecf3a4a1464f6739c0e3e4297bcd90d3d6c2f016ce5"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/imu.service ${D}${systemd_system_unitdir}

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-imu-${PV} ${D}${bindir}/imu
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "imu.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${bindir}"
