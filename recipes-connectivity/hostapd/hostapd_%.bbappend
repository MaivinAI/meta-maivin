FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " file://hostapd.network"
SRC_URI += " file://hostapd.service"
SRC_URI += " file://hostapd.conf"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/network

    install -m 0644 ${WORKDIR}/hostapd.conf ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/hostapd.network ${D}${sysconfdir}/systemd/network
}

CONFFILES:${PN} += "${sysconfdir}/systemd/network/hostapd.network"
