DESCRIPTION = "EdgeFirst Sensor Fusion Service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${PN}-LICENSE;md5=3929fde384c07d35ed0d6f0c925f2a12"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://github.com/EdgeFirstAI/fusion/releases/download/v${PV}/edgefirst-fusion-linux-${TARGET_ARCH};downloadfilename=fusion;name=binary \
    https://raw.githubusercontent.com/EdgeFirstAI/fusion/v${PV}/LICENSE;downloadfilename=${PN}-LICENSE;name=license \
    https://maivin.deepviewml.com/services/fusion/models/radarexp-ultra-short-2025.01.tflite;name=radarexp \
    https://maivin.deepviewml.com/services/fusion/models/radarexp-radar-ultra-short-2025.01.tflite;name=radarexp-radar \
    https://maivin.deepviewml.com/services/fusion/models/radarexp-camera-ultra-short-2025.01.tflite;name=radarexp-camera \
    file://fusion.service \
    file://fusion.default \
"
SRC_URI[license.sha256sum] = "acbbda305958ff27afe43eeef4a77d48ef9d99364e772ba319d1d38ae759ae43"
SRC_URI[radarexp.sha256sum] = "3b640a81b1ed1b67855c3e818aa566f8f917489993331bdfcfce2430916e0eb7"
SRC_URI[radarexp-radar.sha256sum] = "4504d4405af4ce09ce998423b0ea01640b024277ab9c6e16f4282079a47bd61b"
SRC_URI[radarexp-camera.sha256sum] = "d6c017ca22ae89b7a9cb016d97775b033934d47df30677849945d3ee1c79a4a5"

BINARY_SHA256SUM[aarch64] = "fdbfc141763c508fbc38c2903b281d5812335bbb0cf6ed4c196e87cf37ad8e9c"
BINARY_SHA256SUM[x86_64] = "4c771a293a20b6dac4009626d36f58936a913d56312feb498540714185ee9b22"

python () {
    arch = d.getVar('TARGET_ARCH')
    sha256 = d.getVarFlag('BINARY_SHA256SUM', arch)
    if sha256:
        d.setVarFlag('SRC_URI', 'binary.sha256sum', sha256)
}

RDEPENDS:${PN} = "tflite"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/fusion.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/fusion.default ${D}${sysconfdir}/default/fusion

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/fusion ${D}${bindir}/fusion

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
