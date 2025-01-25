DESCRIPTION = "Maivin MCAP Replayer"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/replay/maivin-replay-${PV} \
    file://replay.service \
    file://replay.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "d3d626403651ed4ba656a7cc7d139dd5c2e7d93dd87c2b19d0cdbf6484a29865"

DEPENDS = "videostream"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/replay.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/replay.default ${D}${sysconfdir}/default/replay

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-replay-${PV} ${D}${bindir}/replay
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "replay.service"
SYSTEMD_AUTO_ENABLE = "disable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
