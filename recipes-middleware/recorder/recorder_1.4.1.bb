DESCRIPTION = "Maivin MCAP Recorder"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/recorder/edgefirst-recorder-${PV}-linux-${TARGET_ARCH} \
    file://recorder.service \
    file://recorder.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "f19096868041239cc4b4fcccc48743e412b64c1c6dda06b016b10a64ab7c70cf"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/recorder.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/recorder.default ${D}${sysconfdir}/default/recorder

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-recorder-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/recorder
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "recorder.service"
SYSTEMD_AUTO_ENABLE = "disable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
