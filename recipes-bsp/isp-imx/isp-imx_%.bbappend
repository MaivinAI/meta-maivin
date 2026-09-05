FILESEXTRAPATHS:prepend := "${THISDIR}/isp-imx:"

# Maivin: start isp_media_server directly instead of through upstream's
# start_isp.sh -> run.sh wrappers.
#
# Those wrappers identify the board by counting device-tree nodes, reload
# the vvcam kernel modules, kill stale instances, generate sensor
# configuration into their own directory, and then exec isp_media_server.
# On this platform every one of those steps is either wrong, inert, or
# actively harmful:
#
#   - Device-tree counting always concluded "dual". A Maivin carrier
#     declares both sensor ports but only ever populates one, so this
#     initialised two pipelines on a one-sensor board and logged an
#     adapter-open failure for the empty slot on every single start.
#   - The module reload could not work: the modules are loaded at boot by
#     the softdep in imx8_media_dev.conf and are in use, so rmmod fails and
#     run.sh's load_modules() exits 1 immediately. We were already
#     stripping -lm to work around it.
#   - The stale-instance cleanup called pkill, which busybox does not
#     provide, so it announced a kill and killed nothing.
#   - The generated configuration had to be written somewhere writable,
#     which is the only reason the install directory was being mirrored
#     into a tmpfs on every start.
#
# Everything the wrappers computed reduces to two values: which camera to
# open (CAMERA0/CAMERA1/DUAL_CAMERA) and which sensor mode to use (the
# "mode = N" line in Sensor_Entry.cfg). Both are known -- the mode from
# CAMERA_MODE, the camera from which i2c client the sensor actually bound
# to -- so imx8-isp.service passes them straight to isp_media_server and
# maivin-isp-detect resolves the orientation. This replaces the three
# patches that previously added a run.sh configuration, an ISP_CONFIG
# override and a service EnvironmentFile; none of them are reachable once
# the wrappers are out of the path.
#
# sensor_dwe_os08a20_{1080P,4K}_config.json override upstream's generic
# reference dewarp/calibration data with Maivin's own measured OS08A20 +
# lens calibration (camera_matrix/distortion_coeff), recovered from the old
# imx8-isp fork. Without this, edgefirst-camera's CAM_INFO_PATH would
# publish CameraInfo describing someone else's sensor and lens, silently
# wrong for any consumer doing 2D/3D projection (e.g. edgefirst-fusion).
# The 000N-* patches are generated from github.com/EdgeFirstAI/isp-imx
# (branch main), which tracks this otherwise archive-delivered source.
SRC_URI:append = " \
    file://imx8-isp.service \
    file://maivin-isp-detect \
    file://imx8_media_dev.conf \
    file://sensor_dwe_os08a20_1080P_config.json \
    file://sensor_dwe_os08a20_4K_config.json \
    file://0001-OS08a20-report-errno-on-ioctl-failures.patch \
    file://0002-OS08a20-fix-incorrect-and-unsafe-trace-messages.patch \
    file://0003-OS08a20-free-the-sensor-context-when-open-fails.patch \
    file://0004-OS08a20-pass-pclk-not-pclk-to-VVSENSORIOC_S_CLK.patch \
    file://0005-OS08a20-report-the-outcome-of-the-sensor-open.patch \
"

# CAMERA_MODE name and the matching isp_media_server sensor mode index.
# Kept here rather than in the .cfg files so the mode table lives in one
# place; see camera-mode.sh in edgefirst-camera for the consumer side.
MAIVIN_ISP_MODES = "1080p60:0 4k:2"

do_install:append() {
    install -d ${D}${sysconfdir}/modprobe.d
    install -m 0644 ${WORKDIR}/imx8_media_dev.conf ${D}${sysconfdir}/modprobe.d/imx8_media_dev.conf

    # Relocate off /opt: this is a stateless OSTree system where /opt is a
    # symlink into the persistent /var partition, which is not part of the
    # versioned /usr tree OSTree commits -- anything installed there at
    # build time is silently dropped from the deployed image. Done as a
    # post-install move rather than by patching the install destination
    # because that varies between isp-imx versions (Makefile install target
    # in one, an inline do_install() in another) while both land in
    # /opt/imx8-isp/bin.
    if [ -d ${D}/opt/imx8-isp/bin ]; then
        mkdir -p ${D}${libdir}
        mv ${D}/opt/imx8-isp/bin ${D}${libdir}/imx8-isp
        rm -rf ${D}/opt
    fi

    # Upstream leaves these directories mode 0644 -- no execute bit, so they
    # cannot be traversed at all -- apparently inherited from one of its
    # "install -m 644 -D ..." calls creating the parent.
    chmod 0755 ${D}${libdir}/imx8-isp ${D}${libdir}/imx8-isp/dewarp_config

    # Maivin's own OS08A20 + lens calibration, replacing upstream's generic
    # reference values.
    install -m 0644 ${WORKDIR}/sensor_dwe_os08a20_1080P_config.json ${D}${libdir}/imx8-isp/dewarp_config/sensor_dwe_os08a20_1080P_config.json
    install -m 0644 ${WORKDIR}/sensor_dwe_os08a20_4K_config.json ${D}${libdir}/imx8-isp/dewarp_config/sensor_dwe_os08a20_4K_config.json

    # The wrappers are no longer on any path we take. Remove them rather
    # than leave them installed, so nothing can quietly start depending on
    # them again and so their absence is obvious to anyone looking. The
    # debug tools (video_test, vvext, tuningext) stay.
    rm -f ${D}${libdir}/imx8-isp/start_isp.sh ${D}${libdir}/imx8-isp/run.sh

    # One working directory per sensor mode, holding only the two
    # Sensor_Entry.cfg files isp_media_server looks for. Every path inside
    # them is absolute, so the directory needs nothing else and selecting a
    # mode is just a matter of which one the service chdirs into. This is
    # the entire output of upstream's run.sh, precomputed at build time.
    install -d ${D}${libdir}/imx8-isp/modes
    for entry in ${MAIVIN_ISP_MODES}; do
        name=$(echo $entry | cut -d: -f1)
        idx=$(echo $entry | cut -d: -f2)
        install -d ${D}${libdir}/imx8-isp/modes/$name
        for cfg in Sensor0_Entry.cfg Sensor1_Entry.cfg; do
            cat > ${D}${libdir}/imx8-isp/modes/$name/$cfg <<EOF
name = "os08a20"
drv = "${libdir}/imx8-isp/os08a20.drv"
mode = $idx
[mode.0]
xml = "${libdir}/imx8-isp/OS08a20_8M_10_1080p_linear.xml"
dwe = "${libdir}/imx8-isp/dewarp_config/sensor_dwe_os08a20_1080P_config.json"
[mode.1]
xml = "${libdir}/imx8-isp/OS08a20_8M_10_1080p_hdr.xml"
dwe = "${libdir}/imx8-isp/dewarp_config/sensor_dwe_os08a20_1080P_config.json"
[mode.2]
xml = "${libdir}/imx8-isp/OS08a20_8M_10_4k_linear.xml"
dwe = "${libdir}/imx8-isp/dewarp_config/sensor_dwe_os08a20_4K_config.json"
[mode.3]
xml = "${libdir}/imx8-isp/OS08a20_8M_10_4k_hdr.xml"
dwe = "${libdir}/imx8-isp/dewarp_config/sensor_dwe_os08a20_4K_config.json"
EOF
        done
    done

    # Upstream's do_install creates neither of these directories -- it
    # installs the libraries straight into ${libdir} and the binaries into
    # /opt -- so create them before installing into them.
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-isp-detect ${D}${bindir}/maivin-isp-detect

    # Replace upstream's unit, which execs start_isp.sh.
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/imx8-isp.service ${D}${systemd_system_unitdir}/imx8-isp.service
}

FILES:${PN} += " \
    ${sysconfdir}/modprobe.d/imx8_media_dev.conf \
    ${libdir}/imx8-isp \
    ${bindir}/maivin-isp-detect \
"
