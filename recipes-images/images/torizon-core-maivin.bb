SUMMARY = "Torizon for Maivin"
DESCRIPTION = "Torizon for Maivin Platform"

inherit core-image
inherit extrausers

IMAGE_VARIANT = "Maivin"
IMAGE_FEATURES += "ssh-server-openssh"

do_rootfs[cleandirs] += "${IMAGE_ROOTFS}"

TEZI_IMAGE_NAME = "${IMAGE_BASENAME}${IMAGE_BASENAME_SUFFIX}"
IMAGE_NAME = "${IMAGE_BASENAME}${IMAGE_BASENAME_SUFFIX}${IMAGE_VERSION_SUFFIX}"
IMAGE_LINK_NAME = "${IMAGE_BASENAME}${IMAGE_BASENAME_SUFFIX}"

# Enough free space for a full image update
IMAGE_OVERHEAD_FACTOR = "4"

# Base packages
CORE_IMAGE_BASE_INSTALL:append = " \
    tdx-info \
    auto-provisioning \
    provision-device \
    evtest \
    i2c-tools \
    kernel-modules \
    set-hostname \
    systemd-analyze \
    sudo \
    torizon-conf \
    torizon-users \
    tzdata \
    udev-toradex-rules \
    udev-maivin-rules \
    update-overlays \
    usermount \
    avahi-autoipd \
    iproute2 \
    iputils \
    iptables \
    module-init-tools \
    ostree-customize-plymouth \
    ostree-devicetree-overlays \
    ostree-maivin \
    networkmanager \
    networkmanager-nmcli \
    networkmanager-wifi \
    modemmanager \
    mwifiexap \
    dnsmasq \
    mwifiexap \
    dnsmasq \
    wireguard-tools \
    iperf3 \
    iperf3 \
    fluent-bit \
    neofetch \
    mmc-utils \
    cpufrequtils \
    curl \
    htop \
    jq \
    v4l-utils \
    openssh-sftp-server \
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
    visionpack-python \
    visionpack-apps \
    deepview-rt-modelrunner \
    python3-cffi \
    python3-numpy \
    python3-typeguard \
    python3-pip \
    python3-wheel \
    python3-setuptools \
    videostream-camhost \
    camera \
    imu \
    navsat \
    detect \
    recorder \
    gpscfg \
    gpsd \
    gpsd-conf \
    gps-utils \
    parted \
    libgpiod-tools \
    zenohd \
    libzenohc \
    tmux \
    hailo-pci \
    hailo-firmware \
    libhailort \
    hailortcli \
    pyhailort \
"

nss_altfiles_set_users_groups () {
	# Make a temporary directory to be used by pseudo to find the real /etc/passwd,/etc/group
	pseudo_dir=${WORKDIR}/pseudo-rootfs${sysconfdir}
	override_dir=${IMAGE_ROOTFS}${sysconfdir}
	nsswitch_conf=${IMAGE_ROOTFS}${sysconfdir}/nsswitch.conf

	sed -i -e '/^passwd/s/$/ altfiles/' -e '/^group/s/$/ altfiles/' -e '/^shadow/s/$/ altfiles/' ${nsswitch_conf}

	install -d ${pseudo_dir}
	install -m 644 ${override_dir}/passwd ${pseudo_dir}
	install -m 644 ${override_dir}/group ${pseudo_dir}
	install -m 400 ${override_dir}/shadow ${pseudo_dir}
	cp -a ${pseudo_dir}/* ${IMAGE_ROOTFS}${libdir}

	for file in passwd group shadow; do
		cat > ${override_dir}/${file} <<- EOF
			# NSS altfiles module is installed. Default user, group and shadow files are in
			# /usr/lib/
		EOF
		grep -r torizon ${IMAGE_ROOTFS}${libdir}/${file} >> ${override_dir}/${file}
	done
}

# include nss-altfiles support
CORE_IMAGE_BASE_INSTALL:append = ' ${@bb.utils.contains("DISTRO_FEATURES", "stateless-system", "nss-altfiles", "",d)}'
IMAGE_PREPROCESS_COMMAND:append = ' ${@bb.utils.contains("DISTRO_FEATURES", "stateless-system", "nss_altfiles_set_users_groups; ", "",d)}'
PSEUDO_PASSWD:prepend = "${@bb.utils.contains('DISTRO_FEATURES', 'stateless-system', '${WORKDIR}/pseudo-rootfs:', '', d)}"

# due to limited hardware resources, remove Colibri iMX6 Solo 256MB
# from the list of supported IDs in the Tezi image
TORADEX_PRODUCT_IDS:remove:colibri-imx6 = "0014 0016"
