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
SRC_URI[sha256sum] = "7026394ae3d2da2ca4de8192acbb30fc45a1eeb1f6a6cb04ca4b483e7b4e58a1"

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
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"