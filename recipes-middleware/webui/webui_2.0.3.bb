DESCRIPTION = "Maivin Web UI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/maivin-ui/maivin-ui-${PV};name=webui \
    https://maivin.deepviewml.com/services/maivin-ui/maivin-ui-assets-${PV}.tar;subdir=webui;name=assets \
    file://webui.service \
    file://webui.default \
    file://LICENSE \
"
SRC_URI[webui.sha256sum] = "c09bd0d68ae94991e6d1ae25f0caeda8dd5535bfc9a732ca9ba3d33e1c830602"
SRC_URI[assets.sha256sum] = "33e15f148294f37ea5824f022b4cfe3d2477d0bbd57507e9f9000bf0f88ff4a5"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/webui.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/webui.default ${D}${sysconfdir}/default/webui

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-ui-${PV} ${D}${bindir}/webui

    install -d ${D}${datadir}
    cp -r ${S}/webui ${D}${datadir}
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "webui.service"
SYSTEMD_AUTO_ENABLE = "disable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${datadir}"