FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " file://hostapd-network.service"
SRC_URI += " file://hostapd-dhcp.conf"
SRC_URI += " file://hostapd.service"
SRC_URI += " file://hostapd.conf"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}

    install -m 0644 ${WORKDIR}/hostapd.conf ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/hostapd-dhcp.conf ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/hostapd-network.service ${D}${systemd_system_unitdir}
}

SYSTEMD_SERVICE:${PN}:append = " hostapd-network.service"

CONFFILES:${PN} += "${sysconfdir}/hostapd-dhcp.conf"
