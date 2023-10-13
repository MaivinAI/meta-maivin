FILESEXTRAPATHS_append := "${THISDIR}/linux-toradex:"
KBUILD_BUILD_VERSION_append = "-maivin"

SRC_URI += "file://imx8mp-verdin-wifi-maivin.dts;subdir=git/arch/arm64/boot/dts/freescale"
SRC_URI += "file://dac_driver.patch"
SRC_URI += "file://dac_driver_patch_2.patch"
SRC_URI += "file://verdin_linux_kernel_camera_support.patch"
SRC_URI += "file://maivin_econ_patch.patch"
SRC_URI += "file://dac_5571.cfg"
SRC_URI += "file://cameras.cfg"

KERNEL_DEVICETREE += "freescale/imx8mp-verdin-wifi-maivin.dtb"
