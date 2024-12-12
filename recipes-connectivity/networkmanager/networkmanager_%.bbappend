FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://network0.nmconnection"
SRC_URI += "file://network1.nmconnection"
SRC_URI += "file://ethernet1.sh"

PACKAGECONFIG:append = " wifi dnsmasq nmcli"

do_install:append() {
    install -d ${D}${sysconfdir}/NetworkManager/dispatcher.d/pre-up.d
    install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants

    install -m 0600 ${WORKDIR}/network0.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections
    install -m 0600 ${WORKDIR}/network1.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections
    install -m 0744 ${WORKDIR}/ethernet1.sh ${D}${sysconfdir}/NetworkManager/dispatcher.d/pre-up.d

    ln -sf /usr/lib/systemd/system/NetworkManager-dispatcher.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants
}
