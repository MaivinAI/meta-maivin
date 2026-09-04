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
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-fusion.service
    install -m 0644 ${S}/fusion.service ${D}${systemd_system_unitdir}/fusion.service

    install -d ${D}${datadir}/edgefirst/fusion
    install -m 0644 ${S}/radarexp-ultra-short-2025.01.tflite ${D}${datadir}/edgefirst/fusion/radarexp-ultra-short.tflite
    install -m 0644 ${S}/radarexp-radar-ultra-short-2025.01.tflite ${D}${datadir}/edgefirst/fusion/radarexp-radar-ultra-short.tflite
    install -m 0644 ${S}/radarexp-camera-ultra-short-2025.01.tflite ${D}${datadir}/edgefirst/fusion/radarexp-camera-ultra-short.tflite

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-fusion ${D}${sysconfdir}/default/fusion

    # Maivin ships a DRVEGRD radar and enables late radar-vision fusion OOTB.
    # Upstream leaves RADAR_PCD_TOPIC empty (radar fusion off) and defaults
    # BBOX3D_SRC to lidar, which Maivin does not have. Topics are relative
    # to the hostname session namespace and carry no rt/ prefix -- the wire
    # form is {hostname}/radar/targets.
    sed -i 's|^RADAR_PCD_TOPIC = .*|RADAR_PCD_TOPIC = "radar/targets"|' \
        ${D}${sysconfdir}/default/fusion
    sed -i 's|^BBOX3D_SRC = .*|BBOX3D_SRC = "radar"|' \
        ${D}${sysconfdir}/default/fusion

    ln -sf edgefirst-fusion ${D}${bindir}/fusion
}

FILES:${PN} += "${datadir}"
