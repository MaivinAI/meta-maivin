FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://recorder.service \
"

SYSTEMD_SERVICE:${PN} = "recorder.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-recorder.service
    if [ "${UNPACKDIR}" != "" ]; then
        install -m 0644 ${UNPACKDIR}/recorder.service ${D}${systemd_system_unitdir}/recorder.service
    else
        install -m 0644 ${WORKDIR}/recorder.service ${D}${systemd_system_unitdir}/recorder.service
    fi

    ln -sf edgefirst-recorder ${D}${bindir}/recorder
}
