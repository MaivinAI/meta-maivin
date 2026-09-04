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
# Camera Calibration on Maivin -- see CAM_INFO_PATH above
# ---------------------------------------------------------------------------
# This section only documents what CAM_INFO_PATH means on this platform.
# Set the value at its own definition earlier in this file; do NOT add a
# second CAM_INFO_PATH= line here. Both systemd's EnvironmentFile parser
# and maivin-camera-select-mode (which sources this file) resolve a
# duplicated key last-wins, so a trailing assignment silently overrides
# whatever an administrator set at the documented definition.
#
# Left empty, maivin-camera-select-mode (ExecStartPre, see camera-mode.sh)
# resolves CAM_INFO_PATH from CAMERA_MODE at every service start, choosing
# Maivin's own OS08A20 + lens calibration under
# ${libdir}/imx8-isp/dewarp_config/ -- the same files the ISP loads for the
# live dewarp pipeline, so they exist on every device.
#
# Set it to a readable path (e.g. a per-unit measured calibration) to
# override that auto-selection on every service start.
EOF

    install -d ${D}${libdir}/maivin
    install -m 0644 ${S}/camera-mode.sh ${D}${libdir}/maivin/camera-mode.sh

    install -m 0755 ${S}/maivin-camera-select-mode ${D}${bindir}/maivin-camera-select-mode
    install -m 0755 ${S}/maivin-camera-wait-ready ${D}${bindir}/maivin-camera-wait-ready
}

FILES:${PN} += "${libdir}/maivin"
