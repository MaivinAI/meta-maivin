FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://websrv.service \
"

SYSTEMD_SERVICE:${PN} = "websrv.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-websrv.service
    install -m 0644 ${S}/websrv.service ${D}${systemd_system_unitdir}/websrv.service

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-websrv ${D}${sysconfdir}/default/websrv

    ln -sf edgefirst-websrv ${D}${bindir}/websrv
}
