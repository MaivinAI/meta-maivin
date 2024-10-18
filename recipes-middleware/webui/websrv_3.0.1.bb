DESCRIPTION = "Maivin Web UI Server"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/maivin-websrv/maivin-websrv-${PV} \
    file://webui.service \
    file://webui.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "84bc13622311e46f6b9a12f007b66217b95e329dd037d1bf60900877e4474e5a"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/webui.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/webui.default ${D}${sysconfdir}/default/webui

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-websrv-${PV} ${D}${bindir}/websrv
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "webui.service"
SYSTEMD_AUTO_ENABLE = "disable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"