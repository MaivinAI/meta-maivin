FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://replay.service \
"

# libvideostream (pulled in via the videostream RDEPENDS) dlopens
# libhantro.so.1 for H.264 decode; imx-vpu-hantro-daemon provides the
# vsidaemon.service replay.service now depends on.
RDEPENDS:${PN}:append = " imx-vpu-hantro imx-vpu-hantro-daemon"

SYSTEMD_SERVICE:${PN} = "replay.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-replay.service
    install -m 0644 ${S}/replay.service ${D}${systemd_system_unitdir}/replay.service

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-replay ${D}${sysconfdir}/default/replay

    ln -sf edgefirst-replay ${D}${bindir}/replay
}
