SUMMARY = "Torizon for Maivin"
DESCRIPTION = "Torizon for Maivin Platform"

inherit core-image
inherit extrausers

IMAGE_VARIANT = "Maivin"
IMAGE_FEATURES += "ssh-server-openssh bash-completion-pkgs"
IMAGE_FEATURES += "tools-debug"
PACKAGE_EXCLUDE += "packagegroup-tdx-qt5 wayland-qtdemo-launch-cinematicexperience"

do_rootfs[cleandirs] += "${IMAGE_ROOTFS}"

TEZI_IMAGE_NAME = "${IMAGE_BASENAME}${IMAGE_BASENAME_SUFFIX}"
IMAGE_NAME = "${IMAGE_BASENAME}${IMAGE_BASENAME_SUFFIX}${IMAGE_VERSION_SUFFIX}"
IMAGE_LINK_NAME = "${IMAGE_BASENAME}${IMAGE_BASENAME_SUFFIX}"

# Enough free space for a full image update
IMAGE_OVERHEAD_FACTOR = "4"

VIRTUAL-RUNTIME_container_engine = "docker"

# Base system
CORE_IMAGE_BASE_INSTALL:append = " \
    ca-certificates \
    tdx-info \
    auto-provisioning \
    evtest \
    i2c-tools \
    e2fsprogs \
    xfsprogs \
    kernel-modules \
    set-hostname \
    systemd-analyze \
    sudo \
    torizon-conf \
    torizon-users \
    tzdata \
    udev-toradex-rules \
    avahi-autoipd \
    iproute2 \
    iputils \
    iptables \
    module-init-tools \
    ostree-customize-plymouth \
    ostree-devicetree-overlays \
    networkmanager \
    networkmanager-nmcli \
    networkmanager-wifi \
    modemmanager \
    mobile-broadband-provider-info \
    openssh-sftp-server \
    curl \
    htop \
    jq \
    ldd \
    less \
    parted \
    rsync \
    sudo \
    vim-tiny \
"

# Maivin BSP
CORE_IMAGE_BASE_INSTALL:append = " \
    udev-maivin-rules \
    update-overlays \
    ostree-maivin \
    mwifiexap \
    dnsmasq \
    provision-device \
    mdio-tools \
"

# Networking and diagnostics
CORE_IMAGE_BASE_INSTALL:append = " \
    wireguard-tools \
    iperf3 \
    tcpdump \
    ethtool \
    rclone \
    fluent-bit \
    mmc-utils \
    cpufrequtils \
    rac \
    hdparm \
    v4l-utils \
    libgpiod-tools \
    tmux \
    git \
"

# Maivin middleware services
CORE_IMAGE_BASE_INSTALL:append = " \
    packagegroup-maivin-middleware \
"

# Vision and ML stack (from meta-deepview)
CORE_IMAGE_BASE_INSTALL:append = " \
    visionpack-base \
    visionpack-python \
    deepview-rt-modelrunner \
    tensorflow-lite-vx-delegate \
"

# NNStreamer pipeline
CORE_IMAGE_BASE_INSTALL:append = " \
    nnstreamer \
    nnstreamer-tensorflow-lite \
"

# GStreamer
CORE_IMAGE_BASE_INSTALL:append = " \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
"

# Python packages
CORE_IMAGE_BASE_INSTALL:append = " \
    python3-cffi \
    python3-numpy \
    python3-typeguard \
    python3-pip \
    python3-wheel \
    python3-setuptools \
    python3-lzf \
    python3-pypcd4 \
    python3-opencv \
    python3-requests \
    python3-tqdm \
    python3-pyyaml \
    python3-pillow \
    python3-lmdb \
    python3-certifi \
    python3-pycdr2 \
    lmdb \
"

# GPS and time synchronization
CORE_IMAGE_BASE_INSTALL:append = " \
    gpscfg \
    gpsd \
    gpsd-conf \
    gps-utils \
    u-center \
    linuxptp \
    chrony \
    chronyc \
"

# Kinara Ara-2 NPU accelerator
CORE_IMAGE_BASE_INSTALL:append = " \
    ara2 \
    ara2-python \
"

# Docker container runtime
CORE_IMAGE_BASE_INSTALL:append = " \
    ${VIRTUAL-RUNTIME_container_engine} \
    docker-compose \
    docker-compose-up \
    docker-integrity-checker \
    docker-watchdog \
    docker-auto-prune \
    usermount \
"

# Profiling and debug tools
CORE_IMAGE_BASE_INSTALL:append = " \
    perf \
    uftrace \
    valgrind \
    oprofile \
    coz \
    sysbench \
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

EXTRA_USERS_PARAMS += "usermod -a -G docker torizon;"
