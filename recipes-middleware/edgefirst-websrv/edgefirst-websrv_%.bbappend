FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://websrv.service \
"

SYSTEMD_SERVICE:${PN} = "websrv.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-websrv.service
    if [ "${UNPACKDIR}" != "" ]; then
        install -m 0644 ${UNPACKDIR}/websrv.service ${D}${systemd_system_unitdir}/websrv.service
    else
        install -m 0644 ${WORKDIR}/websrv.service ${D}${systemd_system_unitdir}/websrv.service
    fi

    ln -sf edgefirst-websrv ${D}${bindir}/websrv
}
