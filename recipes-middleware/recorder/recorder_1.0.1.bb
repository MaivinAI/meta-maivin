DESCRIPTION = "Maivin MCAP Recorder"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/recorder/maivin-recorder-${PV} \
    file://recorder.service \
    file://recorder.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "3a2b676f85ec25fcea0eed06a93d5b3f8226cdb8cb2c5e64b9feae66f40c7409"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/recorder.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/recorder.default ${D}${sysconfdir}/default/recorder

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-recorder-${PV} ${D}${bindir}/recorder
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "recorder.service"
SYSTEMD_AUTO_ENABLE = "disable"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
