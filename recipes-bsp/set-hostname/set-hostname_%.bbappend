inherit features_check systemd

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "set-hostname.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    install -d ${D}${sysconfdir}
    echo -n '' > ${D}${sysconfdir}/hostname
}

FILES:${PN} += "${sysconfdir}/hostname"
