FILESEXTRAPATHS:prepend := "${THISDIR}/isp-imx:"

# Maivin: our OS08A20 sensor sits on either CSI0 or CSI1 depending on
# physical camera orientation (landscape vs portrait), and the board's
# device tree always defines both slots, so start_isp.sh's own
# device-tree-node-counting auto-detection always picks the "dual" path
# regardless of which port is actually populated -- DUAL_CAMERA mode
# initializes both slots and is robust to that. dual_os08a20_4k doesn't
# exist upstream (only the 1080p dual configs do), and start_isp.sh has no
# way to be told which configuration to use short of re-running the
# device-tree detection -- so these patches add the missing 4K dual
# configuration and a minimal ISP_CONFIG env var override, letting
# maivin-camera-select-mode (edgefirst-camera) pick the configuration
# explicitly instead of re-deriving it. Everything else (calibration XML,
# DWE tuning, Sensor_Entry.cfg generation) is upstream's, unmodified.
#
# 0002 also strips -lm from every "exec ./run.sh ... -lm" in start_isp.sh
# (both the original device-tree branches and our ISP_CONFIG override).
# Our kernel modules are always already loaded at boot via the softdep in
# imx8_media_dev.conf and in active use, so run.sh's rmmod/insmod reload
# cycle fails outright -- rmmod can't remove an in-use module -- and
# load_modules() exits 1 immediately. That hit on every boot, not just our
# override path: imx8-isp.service failed within milliseconds regardless of
# ISP_CONFIG being set.
#
# 0004 relocates the install destination from /opt/imx8-isp/bin to
# ${libdir}/imx8-isp: this is a stateless OSTree system where /opt is a
# symlink into the persistent /var partition (/var/rootdirs/opt), which is
# NOT part of the versioned /usr tree OSTree actually commits -- anything
# installed under /opt at build time is silently dropped from the deployed
# image. run.sh/start_isp.sh already resolve their own paths relative to
# $(dirname $0), so only the Makefile's install destination and the
# service's ExecStart= needed updating.
SRC_URI:append = " \
    file://0001-run.sh-add-dual_os08a20_4k-configuration.patch \
    file://0002-start_isp.sh-honour-ISP_CONFIG-override.patch \
    file://0003-imx8-isp.service-add-EnvironmentFile-for-ISP_CONFIG.patch \
    file://0004-Makefile-install-under-libdir-not-opt.patch \
    file://imx8_media_dev.conf \
"

# Maivin: imx8_media_dev must wait for the sensor drivers to bind before
# it probes -- os08a20 for Maivin 2, ar0521 for Maivin 1 (meta-toradex-econ)
# -- not shipped by upstream, which doesn't know about either board variant.
do_install:append() {
    install -d ${D}${sysconfdir}/modprobe.d
    install -m 0644 ${WORKDIR}/imx8_media_dev.conf ${D}${sysconfdir}/modprobe.d/imx8_media_dev.conf

    # Upstream's Makefile leaves ${libdir}/imx8-isp (and dewarp_config/)
    # mode 0644 -- no execute bit, so the directory can't be traversed at
    # all -- apparently inherited from one of its "install -m 644 -D ..."
    # calls creating the parent directory. Fix it up rather than patching
    # further into the Makefile's install logic.
    chmod 0755 ${D}${libdir}/imx8-isp ${D}${libdir}/imx8-isp/dewarp_config
}

FILES:${PN} += "${sysconfdir}/modprobe.d/imx8_media_dev.conf ${libdir}/imx8-isp"
