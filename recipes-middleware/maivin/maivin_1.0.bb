DESCRIPTION = "Maivin Middleware Services"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://LICENSE"
SRC_URI += "file://maivin.target"

S = "${WORKDIR}"

inherit features_check systemd

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/maivin.target ${D}${systemd_system_unitdir}
}

REQUIRED_DISTRO_FEATURES = "systemd"

RDEPENDS:${PN} = "imu navsat camera model webui"

FILES:${PN} += "${systemd_system_unitdir}"
