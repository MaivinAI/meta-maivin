FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://navsat.service \
"

SYSTEMD_SERVICE:${PN} = "navsat.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-navsat.service
    install -m 0644 ${S}/navsat.service ${D}${systemd_system_unitdir}/navsat.service

    ln -sf edgefirst-navsat ${D}${bindir}/navsat
}
