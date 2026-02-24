FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://radarpub.service \
"

SYSTEMD_SERVICE:${PN} = "radarpub.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-radarpub.service
    install -m 0644 ${UNPACKDIR}/radarpub.service ${D}${systemd_system_unitdir}/radarpub.service

    ln -sf edgefirst-radarpub ${D}${bindir}/radarpub
}
