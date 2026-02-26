FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://imu.service \
"

SYSTEMD_SERVICE:${PN} = "imu.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-imu.service
    install -m 0644 ${S}/imu.service ${D}${systemd_system_unitdir}/imu.service

    ln -sf edgefirst-imu ${D}${bindir}/imu
}
