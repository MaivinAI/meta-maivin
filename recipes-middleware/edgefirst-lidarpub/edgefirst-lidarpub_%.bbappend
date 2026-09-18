FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://lidarpub.service \
    file://maivin-e1r.default \
"

SYSTEMD_SERVICE:${PN} = "lidarpub.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-lidarpub.service
    install -m 0644 ${S}/lidarpub.service ${D}${systemd_system_unitdir}/lidarpub.service

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-lidarpub ${D}${sysconfdir}/default/lidarpub

    # maivin-e1r sensor profile, installed to /etc/default/lidarpub by
    # `maivin-provision --fix --platform maivin-e1r` (see check_lidarpub).
    install -d ${D}${datadir}/maivin/lidarpub
    install -m 0644 ${S}/maivin-e1r.default ${D}${datadir}/maivin/lidarpub/maivin-e1r.default

    ln -sf edgefirst-lidarpub ${D}${bindir}/lidarpub
}

FILES:${PN} += "${datadir}/maivin"
