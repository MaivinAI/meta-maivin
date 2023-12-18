DESCRIPTION = "Maivin NavSat Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    git://bitbucket.org/au-zone/maivin-navsat.git;protocol=ssh;branch=master;name=navsat \
    git://github.com/MaivinAI/zenoh-ros-type.git;protocol=https;branch=main;name=zenoh-ros-type;destsuffix=zenoh-ros-type \
    file://navsat.service \
"

include navsat.inc

SRCREV_FORMAT = "navsat_zenoh-ros-type"
SRCREV_navsat = "6aab7031d96dfd15057b03d4b130e1ac8cd34c7e"
SRCREV_zenoh-ros-type = "400cb0729b73f7d9da6ca6e6e5e539e1b87569e1"
DEPENDS = "gpsd"
RDEPENDS-${PN} = "gpsd"

S = "${WORKDIR}/git"

inherit features_check systemd pkgconfig cargo

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/navsat.service ${D}${systemd_system_unitdir}
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "navsat.service"
SYSTEMD_AUTO_ENABLE = "disable"

FILES:${PN} += "${systemd_system_unitdir}"
