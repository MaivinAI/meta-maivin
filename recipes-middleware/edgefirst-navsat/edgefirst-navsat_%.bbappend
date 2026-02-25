FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://navsat.service \
"

SYSTEMD_SERVICE:${PN} = "navsat.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-navsat.service
    if [ "${UNPACKDIR}" != "" ]; then
        install -m 0644 ${UNPACKDIR}/navsat.service ${D}${systemd_system_unitdir}/navsat.service
    else
        install -m 0644 ${WORKDIR}/navsat.service ${D}${systemd_system_unitdir}/navsat.service
    fi

    ln -sf edgefirst-navsat ${D}${bindir}/navsat
}
