FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://imu.service \
    file://maivin-imu-configure \
"

SYSTEMD_SERVICE:${PN} = "imu.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-imu.service
    install -m 0644 ${S}/imu.service ${D}${systemd_system_unitdir}/imu.service

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-imu ${D}${sysconfdir}/default/imu

    ln -sf edgefirst-imu ${D}${bindir}/imu

    install -m 0755 ${S}/maivin-imu-configure ${D}${bindir}/maivin-imu-configure
}
