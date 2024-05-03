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
SRC_URI[sha256sum] = "7da0a7a459527ac29e518de5a0e2b5d7c9eb78ca25d7139c5ef4957d9650d05c"

DEPENDS = "videostream"
RDEPENDS-${PN} = "imx8-isp"

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

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${sysconfdir}"