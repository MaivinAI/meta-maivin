DESCRIPTION = "EdgeFirst MCAP Replay"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/replay/edgefirst-replay-${PV}-linux-${TARGET_ARCH} \
    file://replay.service \
    file://replay.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "ec6531189ce6b651039d2374b97ca4e108e83a6397cc972dfad8dfe56035828a"

DEPENDS = "videostream"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/replay.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/replay.default ${D}${sysconfdir}/default/replay

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-replay-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/replay
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "replay.service"
SYSTEMD_AUTO_ENABLE = "disable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
