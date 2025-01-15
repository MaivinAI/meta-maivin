DESCRIPTION = "Maivin Camera Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/camera/maivin-camera-${PV} \
    file://camera.service \
    file://camera.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "57570258ad0b1475685c8c33c26a5d6820e3a15f4e98ecfb1b9bf77a61f806d5"

DEPENDS = "videostream"
RDEPENDS-${PN} = "isp-imx"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/camera.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/camera.default ${D}${sysconfdir}/default/camera

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-camera-${PV} ${D}${bindir}/camera
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "camera.service"
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${sysconfdir}"