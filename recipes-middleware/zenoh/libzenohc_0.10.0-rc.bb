DESCRIPTION = "Zenoh C API"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "\
    git://github.com/eclipse-zenoh/zenoh-c.git;protocol=ssh;branch=release-0.10.0-rc \
    file://0001-Removed-panic-strategy-from-release-builds-to-suppor.patch \
"
SRCREV = "5993ddb87f761d9f832bee81f4ef61f07fdeaec6"

include libzenohc.inc

inherit cmake cargo

S = "${WORKDIR}/git"
EXTRA_OECMAKE = "-DZENOHC_CUSTOM_TARGET=${TARGET_ARCH}-unknown-linux-gnu"
