FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://recorder.service \
"

SYSTEMD_SERVICE:${PN} = "recorder.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-recorder.service
    install -m 0644 ${S}/recorder.service ${D}${systemd_system_unitdir}/recorder.service

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-recorder ${D}${sysconfdir}/default/recorder

    ln -sf edgefirst-recorder ${D}${bindir}/recorder
}
