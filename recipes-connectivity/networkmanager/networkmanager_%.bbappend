FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://network0.nmconnection"
SRC_URI += "file://network1.nmconnection"

PACKAGECONFIG:append = " wifi dnsmasq nmcli"

do_install:append() {
    install -m 0600 ${WORKDIR}/network0.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections
    install -m 0600 ${WORKDIR}/network1.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections
}
