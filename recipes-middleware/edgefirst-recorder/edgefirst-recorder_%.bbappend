FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://recorder.service \
"

SYSTEMD_SERVICE:${PN} = "recorder.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-recorder.service
    install -m 0644 ${UNPACKDIR}/recorder.service ${D}${systemd_system_unitdir}/recorder.service

    ln -sf edgefirst-recorder ${D}${bindir}/recorder
}
