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
SRC_URI[webui.sha256sum] = "12e970af12b750bfe6dd3a7464aae0c00c7bbd25ce2a97d1629898c00c60f201"
SRC_URI[assets.sha256sum] = "c6f213177c531f3f959ce5a5e5e07e419e80e119121b3218abc4144d94a9444a"

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