inherit toradex-devicetree

SRC_URI = "git://github.com/MaivinAI/maivin-overlays.git;protocol=ssh;branch=main"
SRCREV = "8e8c3217b9201a39ac4162f69710c8c16134e78f"
SRCREV:use-head-next = "${AUTOREV}"
SRCBRANCH = "main"

KERNEL_INCLUDE = " \
    ${STAGING_KERNEL_DIR}/arch/arm64/boot/dts \
    ${STAGING_KERNEL_DIR}/arch/arm64/boot/dts/* \
    ${STAGING_KERNEL_DIR}/scripts/dtc/include-prefixes \
"

COMPATIBLE_MACHINE = ".*(mx[8]).*"

TEZI_EXTERNAL_KERNEL_DEVICETREE_BOOT = ""
TEZI_EXTERNAL_KERNEL_DEVICETREE = "\
    maivin1.dtbo \
    maivin1-ar0521.dtbo \
    maivin1-os08a20.dtbo \
    maivin1-m2pcie.dtbo \
    maivin1-m2usb.dtbo \
    maivin1-gps.dtbo \
    maivin2.dtbo \
    maivin2-m2pcie.dtbo \
    maivin2-m2usb.dtbo \
    maivin2-os08a20.dtbo \
"
