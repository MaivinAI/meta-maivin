DESCRIPTION = "Camera Pose Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    git://bitbucket.org/au-zone/camerapose.git;protocol=ssh;branch=raivin;name=camerapose \
    git://bitbucket.org/au-zone/bno08x-rs.git;protocol=ssh;branch=master;name=bno08x;destsuffix=bno08x-rs \
    git://github.com/MaivinAI/zenoh-ros-type.git;protocol=https;branch=main;name=zenoh-ros-type;destsuffix=zenoh-ros-type \
    file://0001-Use-local-bno08x-rs-repository.patch \
    file://camerapose.service \
"

include camerapose.inc

SRCREV_FORMAT = "camerapose_bno08x-zenoh-ros-type"
SRCREV_camerapose = "00fb94fb40b94897a414b259a120cbc8452acb0e"
SRCREV_bno08x = "b614b976f2edfbdcf141b5a75ceb021f22d4cd3b"
SRCREV_zenoh-ros-type = "dccfc85e0284d3f1663d960bdb686c867895d51c"

S = "${WORKDIR}/git"

inherit features_check systemd pkgconfig cargo

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/camerapose.service ${D}${systemd_system_unitdir}
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "camerapose.service"
SYSTEMD_AUTO_ENABLE = "disable"

FILES:${PN} += "${bindir}"
FILES:${PN} += "${systemd_system_unitdir}"
