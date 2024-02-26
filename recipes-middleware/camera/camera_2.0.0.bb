DESCRIPTION = "Maivin Camera Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    git://bitbucket.org/au-zone/maivin-camera.git;protocol=ssh;branch=RVN-185;name=camera \
    git://github.com/MaivinAI/zenoh-ros-type.git;protocol=https;branch=main;name=zenoh-ros-type;destsuffix=zenoh-ros-type \
    file://camera.service \
"

include camera.inc

SRCREV_FORMAT = "camera_zenoh-ros-type"
SRCREV_camera = "56e707de3344e29e893940f4598d0092be7294d3"
SRCREV_zenoh-ros-type = "dccfc85e0284d3f1663d960bdb686c867895d51c"
DEPENDS = "videostream"
RDEPENDS-${PN} = "imx8-isp"

S = "${WORKDIR}/git"

inherit features_check systemd pkgconfig cargo

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/camera.service ${D}${systemd_system_unitdir}
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "camera.service"
SYSTEMD_AUTO_ENABLE = "disable"

FILES:${PN} += "${systemd_system_unitdir}"
