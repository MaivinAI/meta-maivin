DESCRIPTION = "i.MX Verisilicon Software ISP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"
DEPENDS = "libdrm virtual/libg2d libtinyxml2"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    ${FSL_MIRROR}/isp-imx-${PV}.bin;fsl-eula=true \
    file://imx8-isp.service \
    file://OS08A20_MODES.txt \
    file://Sensor0_Entry.cfg \
    file://Sensor1_Entry.cfg \
    file://dewarp_config/sensor_dwe_os08a20_4K_config.json \
    file://dewarp_config/sensor_dwe_os08a20_1080P_config.json \
"
SRC_URI[md5sum] = "01f83394df91091f414f122c339c02bc"
SRC_URI[sha256sum] = "ab65a413f397230010266579df570beac5fde4af430e31fc251d7cf7c8fa2232"

inherit fsl-eula-unpack cmake features_check systemd use-imx-headers

S = "${WORKDIR}/isp-imx-${PV}"

# Build the sub-folder appshell
OECMAKE_SOURCEPATH = "${S}/appshell"

# Use make instead of ninja
OECMAKE_GENERATOR = "Unix Makefiles"

# Workaround for linking issues seen with gold linker
LDFLAGS:append = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)}"

EXTRA_OECMAKE += " \
    -DSDKTARGETSYSROOT=${STAGING_DIR_HOST} \
    -DCMAKE_BUILD_TYPE=release \
    -DCMAKE_BUILD_WITH_INSTALL_RPATH=ON \
    -DISP_VERSION=ISP8000NANO_V1802 \
    -DPLATFORM=ARM64 \
    -DAPPMODE=V4L2 \
    -DQTLESS=1 \
    -DFULL_SRC_COMPILE=1 \
    -DWITH_DRM=1 \
    -DWITH_DWE=1 \
    -DSERVER_LESS=1 \
    -DSUBDEV_V4L2=1 \
    -DENABLE_IRQ=1 \
    -DPARTITION_BUILD=0 \
    -D3A_SRC_BUILD=0 \
    -DIMX_G2D=ON \
    -Wno-dev \
"

do_install() {
    install -d ${D}/${bindir}
    install -d ${D}/${libdir}
    install -d ${D}/${includedir}
    install -d ${D}/${datadir}/imx8-isp
    install -d ${D}/${datadir}/imx8-isp/dewarp_config

    cp ${WORKDIR}/OS08A20_MODES.txt ${D}/${datadir}/imx8-isp
    cp ${WORKDIR}/Sensor0_Entry.cfg ${D}/${datadir}/imx8-isp
    cp ${WORKDIR}/Sensor1_Entry.cfg ${D}/${datadir}/imx8-isp

    cp ${B}/generated/release/bin/*.xml ${D}/${datadir}/imx8-isp
    cp ${B}/generated/release/bin/*.drv ${D}/${datadir}/imx8-isp
    cp ${S}/dewarp/dewarp_config/*.json ${D}/${datadir}/imx8-isp/dewarp_config

    # Copy local dewarp_config over vendor version.
    cp ${WORKDIR}/dewarp_config/*.json ${D}/${datadir}/imx8-isp/dewarp_config

    cp ${B}/generated/release/bin/isp_media_server ${D}/${bindir}
    cp -d ${B}/generated/release/lib/*.so* ${D}/${libdir}

    # Provided as versioned libraries so remove the .so to keep Yocto happy.
    rm -f ${D}/${libdir}/libos08a20.so
    rm -f ${D}/${libdir}/libar1335.so
    rm -f ${D}/${libdir}/libov2775.so
    rm -f ${D}/${libdir}/libjsoncpp.so

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/imx8-isp.service ${D}${systemd_system_unitdir}
}

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "imx8-isp.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES_SOLIBSDEV = ""

FILES:${PN} += "${libdir}"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${datadir}"

RDEPENDS:${PN} = "libdrm"
