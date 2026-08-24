FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://radarpub.service \
    file://radarpub.default \
"

SYSTEMD_SERVICE:${PN} = "radarpub.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-radarpub.service
    install -m 0644 ${S}/radarpub.service ${D}${systemd_system_unitdir}/radarpub.service

    # Maivin default: low/ultra-short range. The example model we ship is
    # trained for ultra-short range, which upstream's default (medium/short)
    # does not provide -- override rather than rename.
    rm -f ${D}${sysconfdir}/default/edgefirst-radarpub
    install -m 0644 ${S}/radarpub.default ${D}${sysconfdir}/default/radarpub

    ln -sf edgefirst-radarpub ${D}${bindir}/radarpub
}
