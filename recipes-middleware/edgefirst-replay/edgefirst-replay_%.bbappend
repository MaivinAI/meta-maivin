FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://replay.service \
"

SYSTEMD_SERVICE:${PN} = "replay.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-replay.service
    if [ "${UNPACKDIR}" != "" ]; then
        install -m 0644 ${UNPACKDIR}/replay.service ${D}${systemd_system_unitdir}/replay.service
    else
        install -m 0644 ${WORKDIR}/replay.service ${D}${systemd_system_unitdir}/replay.service
    fi

    ln -sf edgefirst-replay ${D}${bindir}/replay
}
