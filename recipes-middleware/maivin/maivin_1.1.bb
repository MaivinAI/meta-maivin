DESCRIPTION = "Maivin Platform Configuration"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://maivin.target"
SRC_URI += "file://ethernet1-master.service"
SRC_URI += "file://can0.service"
SRC_URI += "file://ethernet1-radar.nmconnection"
SRC_URI += "file://ethernet1-lidar.nmconnection"

S = "${@d.getVar('UNPACKDIR') or d.getVar('WORKDIR')}"

inherit features_check systemd

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${sysconfdir}/systemd/system/network.target.wants
    install -d ${D}${sysconfdir}/NetworkManager/system-connections

    # Maivin middleware target
    install -m 0644 ${S}/maivin.target ${D}${systemd_system_unitdir}
    ln -sf maivin.target ${D}${systemd_system_unitdir}/raivin.target

    # Automotive ethernet master and CAN bus
    install -m 0644 ${S}/ethernet1-master.service ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/can0.service ${D}${systemd_system_unitdir}

    # Sensor network profiles (radar default, switch via nmcli during provisioning)
    install -m 0600 ${S}/ethernet1-radar.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections
    install -m 0600 ${S}/ethernet1-lidar.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections

    ln -sf ${systemd_system_unitdir}/ethernet1-master.service ${D}${sysconfdir}/systemd/system/network.target.wants
    ln -sf ${systemd_system_unitdir}/can0.service ${D}${sysconfdir}/systemd/system/network.target.wants

    # Disable systemd-networkd: NetworkManager is the sole network manager
    ln -sf /dev/null ${D}${sysconfdir}/systemd/system/systemd-networkd.service
    ln -sf /dev/null ${D}${sysconfdir}/systemd/system/systemd-networkd.socket
    ln -sf /dev/null ${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service
}

REQUIRED_DISTRO_FEATURES = "systemd"

RDEPENDS:${PN} = "edgefirst-imu edgefirst-navsat edgefirst-camera edgefirst-model edgefirst-modelzoo edgefirst-webui edgefirst-radarpub edgefirst-fusion"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
