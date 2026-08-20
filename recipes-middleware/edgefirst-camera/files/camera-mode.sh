# Shared OS08A20 camera mode table, sourced by maivin-camera-select-mode
# (ExecStartPre, writes the selected upstream isp-imx run.sh configuration
# name to /etc/default/isp before imx8-isp.service (re)starts -- see the
# ISP_CONFIG override added to start_isp.sh by meta-maivin's isp-imx
# bbappend patches).
#
# CAMERA_MODE selects the sensor's native readout mode -- it is independent
# of CAMERA_SIZE in /etc/default/camera: the ISP's own dewarp/scale stage
# downscales from the native mode to whatever CAMERA_SIZE the client
# requests (confirmed in isp_media_server's own logs -- it scales a 4K
# native capture down to a smaller requested size just fine). CAMERA_SIZE
# must not exceed the selected mode's native resolution -- the ISP does not
# upscale.
#
# Both configs use DUAL_CAMERA (isp-imx's run.sh -c dual_os08a20_*): our
# sensor sits on either CSI0 or CSI1 depending on physical camera
# orientation (landscape vs portrait), and the board's device tree always
# defines both slots, so a single-camera CAMERA0-only configuration would
# miss a CSI1-populated board. DUAL_CAMERA initializes both slots and is
# robust to either.
resolve_camera_mode() {
    : "${CAMERA_MODE:=4k}"
    case "$CAMERA_MODE" in
        4k)
            ISP_CONFIG="dual_os08a20_4k"
            ;;
        1080p60)
            ISP_CONFIG="dual_os08a20_1080p60"
            ;;
        *)
            echo "camera-mode: unknown CAMERA_MODE '$CAMERA_MODE', defaulting to 4k" >&2
            CAMERA_MODE=4k
            ISP_CONFIG="dual_os08a20_4k"
            ;;
    esac
}
