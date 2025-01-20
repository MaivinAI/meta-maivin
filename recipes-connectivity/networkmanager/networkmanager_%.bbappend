FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://network0.nmconnection"
SRC_URI += "file://unmanaged.conf"

PACKAGECONFIG:append = " wifi dnsmasq nmcli"

do_install:append() {
    install -m 0600 ${WORKDIR}/network0.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections
    install -m 0600 ${WORKDIR}/unmanaged.conf ${D}${sysconfdir}/NetworkManager/conf.d
    
    # remove 99-disable-uap.conf which is part of the generic unmanaged.conf file.
    rm ${D}${sysconfdir}/NetworkManager/conf.d/99-disable-uap.conf
}
