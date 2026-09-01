FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://camera.service \
    file://camera-mode.sh \
    file://maivin-camera-select-mode \
    file://maivin-camera-wait-ready \
"

RDEPENDS:${PN}:append = " isp-imx imx-vpu-hantro-vc v4l-utils"

SYSTEMD_SERVICE:${PN} = "camera.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    # Replace edgefirst-prefixed service with Maivin-named service
    rm -f ${D}${systemd_system_unitdir}/edgefirst-camera.service
    install -m 0644 ${S}/camera.service ${D}${systemd_system_unitdir}/camera.service

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-camera ${D}${sysconfdir}/default/camera

    # Add short-name symlink for binary
    ln -sf edgefirst-camera ${D}${bindir}/camera

    # CAMERA_MODE selects the OS08A20 sensor's native readout mode -- see
    # camera-mode.sh for the mode table.
    cat >> ${D}${sysconfdir}/default/camera <<'EOF'

# ---------------------------------------------------------------------------
# Camera Mode
# ---------------------------------------------------------------------------
# Selects the OS08A20 sensor's native readout mode, applied by restarting
# imx8-isp.service (isp-imx) with the matching upstream run.sh
# configuration before this service starts. Independent of CAMERA_SIZE
# above: the ISP downscales from the native mode to whatever CAMERA_SIZE is
# requested, so CAMERA_SIZE may be smaller than the native mode's
# resolution (e.g. 4k sensor mode with a smaller CAMERA_SIZE for lower
# bandwidth/CPU use) but must not exceed it -- the ISP does not upscale.
# Accepted values: 4k, 1080p60
#   4k       - 3840x2160, BGGR12, full-resolution linear, 30fps
#   1080p60  - 1920x1080, BGGR10, horizontal-binned, 60fps
#
# Maivin default: 1080p60. Binned readout gives better low-light/
# fast-exposure handling than 4k's full-resolution linear readout, which
# is what the shipped example model is tuned for; 4k is a known separate
# issue (unreliable capture, tracked independently) not a hard requirement.
CAMERA_MODE="1080p60"

# ---------------------------------------------------------------------------
# Camera Calibration (Maivin override)
# ---------------------------------------------------------------------------
# Overrides the empty upstream default. isp-imx's start_isp.sh copies its
# dewarp_config/*.json (Maivin's own OS08A20 + lens calibration -- see
# isp-imx_%.bbappend) out to /etc/isp/ at runtime; this must match
# CAMERA_MODE above (dual_os08a20_4k currently isn't covered here, since
# 4k capture is already a known separate issue -- see camera-mode.sh).
# Without this, camera never publishes CameraInfo on camera/info, and
# consumers that require it (e.g. edgefirst-fusion) can't start.
CAM_INFO_PATH="/etc/isp/sensor_dwe_os08a20_1080P_config.json"
EOF

    install -d ${D}${libdir}/maivin
    install -m 0644 ${S}/camera-mode.sh ${D}${libdir}/maivin/camera-mode.sh

    install -m 0755 ${S}/maivin-camera-select-mode ${D}${bindir}/maivin-camera-select-mode
    install -m 0755 ${S}/maivin-camera-wait-ready ${D}${bindir}/maivin-camera-wait-ready
}

FILES:${PN} += "${libdir}/maivin"
