DESCRIPTION = "EdgeFirst Camera Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/camera/edgefirst-camera-${PV}-linux-${TARGET_ARCH} \
    file://camera.service \
    file://camera.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "be83ac0d5eb9252a023dba6d54a77282420d4521ad22a4fbacdb217e9e4cb021"

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
    install -m 0755 ${WORKDIR}/edgefirst-camera-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/camera
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "camera.service"
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${sysconfdir}"