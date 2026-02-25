FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://lidarpub.service \
"

SYSTEMD_SERVICE:${PN} = "lidarpub.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-lidarpub.service
    if [ "${UNPACKDIR}" != "" ]; then
        install -m 0644 ${UNPACKDIR}/lidarpub.service ${D}${systemd_system_unitdir}/lidarpub.service
    else
        install -m 0644 ${WORKDIR}/lidarpub.service ${D}${systemd_system_unitdir}/lidarpub.service
    fi

    ln -sf edgefirst-lidarpub ${D}${bindir}/lidarpub
}
