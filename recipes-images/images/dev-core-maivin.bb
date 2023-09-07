inherit core-image

SUMMARY = "Maivin Development System"
DESCRIPTION = "Maivin Development System provides a minimal development image with DeepView AI Middleware but without Torizon"

IMAGE_FEATURES:append = " package-management debug-tweaks tools-debug tools-profile ssh-server-openssh"

IMAGE_BASENAME_SUFFIX ?= ""
TEZI_IMAGE_NAME = "${IMAGE_BASENAME}${IMAGE_BASENAME_SUFFIX}"
IMAGE_NAME = "${IMAGE_BASENAME}${IMAGE_BASENAME_SUFFIX}${IMAGE_VERSION_SUFFIX}"
IMAGE_LINK_NAME = "${IMAGE_BASENAME}${IMAGE_BASENAME_SUFFIX}"

# Base packages
CORE_IMAGE_BASE_INSTALL:append = " \
    tdx-info \
    evtest \
    i2c-tools \
    kernel-modules \
    set-hostname \
    systemd-analyze \
    tzdata \
    udev-toradex-rules \
    udev-maivin-rules \
    iproute2 \
    iputils \
    iptables \
    module-init-tools \
    networkmanager \
    networkmanager-nmcli \
    networkmanager-wifi \
    modemmanager \
    wireguard-tools \
    mmc-utils \
    cpufrequtils \
    curl \
    htop \
    jq \
    v4l-utils \
    rsync \
    vim-tiny \
    kernel-module-isp-vvcam \
    kernel-module-imx-gpu-viv \
    imx8-isp \
    imx-vpu-hantro \
    imx-vpu-hantro-vc \
    imx-vpu-hantro-daemon \
    imx-vpuwrap \
    imx-gpu-viv \
    libglslc-imx-dev \
    visionpack-base \
    videostream-camhost \
    gpscfg \
    gpsd \
    gpsd-conf \
    gps-utils \
    libgpiod-tools \
"

add_rootfs_version () {
    printf "${DISTRO_NAME} ${DISTRO_VERSION} (${DISTRO_CODENAME}) \\\n \\\l\n" > ${IMAGE_ROOTFS}/etc/issue
    printf "${DISTRO_NAME} ${DISTRO_VERSION} (${DISTRO_CODENAME}) %%h\n" > ${IMAGE_ROOTFS}/etc/issue.net
    printf "${IMAGE_NAME}\n\n" >> ${IMAGE_ROOTFS}/etc/issue
    printf "${IMAGE_NAME}\n\n" >> ${IMAGE_ROOTFS}/etc/issue.net
}

automount_data () {
    cat >> ${IMAGE_ROOTFS}${sysconfdir}/fstab <<EOF
/dev/mmcblk2p1 /boot auto defaults 0 0
EOF
}

# add the rootfs version to the welcome banner and sdcard automount configuration
ROOTFS_POSTPROCESS_COMMAND:append = ' add_rootfs_version; automount_data;'
