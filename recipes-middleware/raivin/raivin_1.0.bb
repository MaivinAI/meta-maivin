DESCRIPTION = "Raivin Middleware Services"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://LICENSE"
SRC_URI += "file://raivin.target"
SRC_URI += "file://ethernet1-master.service"
SRC_URI += "file://ethernet1.network"
SRC_URI += "file://can0.network"

S = "${WORKDIR}"

inherit features_check systemd

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${sysconfdir}/systemd/network
    install -d ${D}${sysconfdir}/systemd/system/network.target.wants

    if [ "${UNPACKDIR}" != "" ]; then
        install -m 0644 ${UNPACKDIR}/raivin.target ${D}${systemd_system_unitdir}
        install -m 0644 ${UNPACKDIR}/ethernet1-master.service ${D}${systemd_system_unitdir}
        install -m 0644 ${UNPACKDIR}/ethernet1.network ${D}${sysconfdir}/systemd/network
        install -m 0644 ${UNPACKDIR}/can0.network ${D}${sysconfdir}/systemd/network
    else
        install -m 0644 ${WORKDIR}/raivin.target ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/ethernet1-master.service ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/ethernet1.network ${D}${sysconfdir}/systemd/network
        install -m 0644 ${WORKDIR}/can0.network ${D}${sysconfdir}/systemd/network
    fi

    ln -sf ${systemd_system_unitdir}/ethernet1-master.service ${D}${sysconfdir}/systemd/system/network.target.wants
}

REQUIRED_DISTRO_FEATURES = "systemd"

RDEPENDS:${PN} = "maivin edgefirst-radarpub edgefirst-fusion"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
