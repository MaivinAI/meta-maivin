DESCRIPTION = "Maivin u-Center Server"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "file://u-center.service"

S = "${WORKDIR}"

inherit features_check systemd

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/u-center.service ${D}${systemd_system_unitdir}
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "u-center.service"
SYSTEMD_AUTO_ENABLE = "disable"

RDEPENDS:${PN} = "ser2net"

FILES:${PN} += "${systemd_system_unitdir}"
