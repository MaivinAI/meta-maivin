FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://imu.service \
"

SYSTEMD_SERVICE:${PN} = "imu.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-imu.service
    if [ "${UNPACKDIR}" != "" ]; then
        install -m 0644 ${UNPACKDIR}/imu.service ${D}${systemd_system_unitdir}/imu.service
    else
        install -m 0644 ${WORKDIR}/imu.service ${D}${systemd_system_unitdir}/imu.service
    fi

    ln -sf edgefirst-imu ${D}${bindir}/imu
}
