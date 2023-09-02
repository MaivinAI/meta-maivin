DESCRIPTION = "libheif is an ISO/IEC 23008-12:2017 HEIF and AVIF (AV1 Image File Format) file format decoder and encoder."
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=f07b2bc152eaee52edbd338825f474d4"

SRCREV = "06f6acfe735379b9c3bcf865285f6dba80611e90"
SRC_URI = "git://github.com/strukturag/libheif.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit cmake

do_install:append () {
    rm -rf ${D}/usr/share/thumbnailers
}

FILES:${PN} += "${libdir}"
