inherit toradex-devicetree

SRC_URI = "git://github.com/MaivinAI/maivin-overlays.git;protocol=ssh;branch=main"
SRCREV = "76ba6a8ab3aa7ee303f1f6057722ae76d8d64633"
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
    maivin1-pcie.dtbo \
    maivin1-gps.dtbo \
    maivin1-ar0521-csi1.dtbo \
    maivin1-os08a20-csi1.dtbo \
    maivin2.dtbo \
    maivin2-pcie.dtbo \
    maivin2-modem.dtbo \
    maivin2-os08a20-csi1.dtbo \
    maivin2-os08a20-csi2.dtbo \
"
