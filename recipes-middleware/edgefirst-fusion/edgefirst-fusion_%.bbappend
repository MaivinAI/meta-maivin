FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://fusion.service \
    https://maivin.deepviewml.com/services/fusion/models/radarexp-ultra-short-2025.01.tflite;name=radarexp \
    https://maivin.deepviewml.com/services/fusion/models/radarexp-radar-ultra-short-2025.01.tflite;name=radarexp-radar \
    https://maivin.deepviewml.com/services/fusion/models/radarexp-camera-ultra-short-2025.01.tflite;name=radarexp-camera \
"
SRC_URI[radarexp.sha256sum] = "3b640a81b1ed1b67855c3e818aa566f8f917489993331bdfcfce2430916e0eb7"
SRC_URI[radarexp-radar.sha256sum] = "4504d4405af4ce09ce998423b0ea01640b024277ab9c6e16f4282079a47bd61b"
SRC_URI[radarexp-camera.sha256sum] = "d6c017ca22ae89b7a9cb016d97775b033934d47df30677849945d3ee1c79a4a5"

SYSTEMD_SERVICE:${PN} = "fusion.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-fusion.service
    install -m 0644 ${S}/fusion.service ${D}${systemd_system_unitdir}/fusion.service

    install -d ${D}${datadir}/fusion
    install -m 0644 ${S}/radarexp-ultra-short-2025.01.tflite ${D}${datadir}/fusion/radarexp-ultra-short.tflite
    install -m 0644 ${S}/radarexp-radar-ultra-short-2025.01.tflite ${D}${datadir}/fusion/radarexp-radar-ultra-short.tflite
    install -m 0644 ${S}/radarexp-camera-ultra-short-2025.01.tflite ${D}${datadir}/fusion/radarexp-camera-ultra-short.tflite

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-fusion ${D}${sysconfdir}/default/fusion

    ln -sf edgefirst-fusion ${D}${bindir}/fusion
}

FILES:${PN} += "${datadir}"
