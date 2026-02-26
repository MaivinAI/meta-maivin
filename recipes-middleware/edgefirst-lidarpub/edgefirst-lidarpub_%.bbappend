FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://lidarpub.service \
"

SYSTEMD_SERVICE:${PN} = "lidarpub.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-lidarpub.service
    install -m 0644 ${S}/lidarpub.service ${D}${systemd_system_unitdir}/lidarpub.service

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-lidarpub ${D}${sysconfdir}/default/lidarpub

    ln -sf edgefirst-lidarpub ${D}${bindir}/lidarpub
}
