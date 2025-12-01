DESCRIPTION = "Maivin Sensor Fusion Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/fusion/edgefirst-fusion-${PV}-linux-${TARGET_ARCH};name=fusion \
    https://maivin.deepviewml.com/services/fusion/models/radarexp-ultra-short-2025.01.tflite;name=radarexp \
    https://maivin.deepviewml.com/services/fusion/models/radarexp-radar-ultra-short-2025.01.tflite;name=radarexp-radar \
    https://maivin.deepviewml.com/services/fusion/models/radarexp-camera-ultra-short-2025.01.tflite;name=radarexp-camera \
    file://fusion.service \
    file://fusion.default \
    file://LICENSE \
"
SRC_URI[fusion.sha256sum] = "d2d2b9fe7eedfd66a8c75d185431e1c3f589146702c39bf61651d2e3be78d163"
SRC_URI[radarexp.sha256sum] = "3b640a81b1ed1b67855c3e818aa566f8f917489993331bdfcfce2430916e0eb7"
SRC_URI[radarexp-radar.sha256sum] = "4504d4405af4ce09ce998423b0ea01640b024277ab9c6e16f4282079a47bd61b"
SRC_URI[radarexp-camera.sha256sum] = "d6c017ca22ae89b7a9cb016d97775b033934d47df30677849945d3ee1c79a4a5"

DEPENDS = "vaal deepview-rt"
RDEPENDS_${PN} = "libvaal libdeepview-rt"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/fusion.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/fusion.default ${D}${sysconfdir}/default/fusion

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-fusion-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/fusion

    install -d ${D}${datadir}/fusion
    install -m 0644 ${WORKDIR}/radarexp-ultra-short-2025.01.tflite ${D}${datadir}/fusion/radarexp-ultra-short.tflite
    install -m 0644 ${WORKDIR}/radarexp-radar-ultra-short-2025.01.tflite ${D}${datadir}/fusion/radarexp-radar-ultra-short.tflite
    install -m 0644 ${WORKDIR}/radarexp-camera-ultra-short-2025.01.tflite ${D}${datadir}/fusion/radarexp-camera-ultra-short.tflite
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "fusion.service"
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${datadir}"
