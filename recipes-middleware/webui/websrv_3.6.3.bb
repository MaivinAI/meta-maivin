DESCRIPTION = "EdgeFirst Web UI Server"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/edgefirst-websrv/edgefirst-websrv-${PV}-linux-${TARGET_ARCH} \
    file://webui.service \
    file://webui.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "a26437df08f565668c979821ee2a5ad06a36a35a0f998880b5b0267b817ae593"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/webui.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/webui.default ${D}${sysconfdir}/default/webui

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-websrv-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/websrv
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "webui.service"
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"