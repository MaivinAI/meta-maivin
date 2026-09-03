# Shared OS08A20 camera mode table, sourced by maivin-camera-select-mode
# (ExecStartPre, which writes the selected sensor mode and the matching
# camera-info calibration path to /etc/default/isp before
# imx8-isp.service (re)starts).
#
# CAMERA_MODE selects the sensor's native readout mode -- it is independent
# of CAMERA_SIZE in /etc/default/camera: the ISP's own dewarp/scale stage
# downscales from the native mode to whatever CAMERA_SIZE the client
# requests. CAMERA_SIZE must not exceed the selected mode's native
# resolution -- the ISP does not upscale.
#
# ISP_MODE names a working directory under /usr/lib/imx8-isp/modes/, each
# holding the Sensor_Entry.cfg pair for that sensor mode; imx8-isp.service
# chdirs into it. See isp-imx_%.bbappend for how those are generated and
# for the CAMERA_MODE-to-sensor-mode-index table.
#
# Which CSI slot is opened is resolved separately at ISP start by
# maivin-isp-detect, from the i2c client the sensor actually bound to. It
# is a property of the fitted camera orientation, not of CAMERA_MODE.
DEWARP_CONFIG_DIR="/usr/lib/imx8-isp/dewarp_config"

resolve_camera_mode() {
    : "${CAMERA_MODE:=4k}"
    case "$CAMERA_MODE" in
        4k)
            ISP_MODE="4k"
            CAM_INFO_PATH="$DEWARP_CONFIG_DIR/sensor_dwe_os08a20_4K_config.json"
            ;;
        1080p60)
            ISP_MODE="1080p60"
            CAM_INFO_PATH="$DEWARP_CONFIG_DIR/sensor_dwe_os08a20_1080P_config.json"
            ;;
        *)
            echo "camera-mode: unknown CAMERA_MODE '$CAMERA_MODE', defaulting to 4k" >&2
            CAMERA_MODE=4k
            ISP_MODE="4k"
            CAM_INFO_PATH="$DEWARP_CONFIG_DIR/sensor_dwe_os08a20_4K_config.json"
            ;;
    esac
}
