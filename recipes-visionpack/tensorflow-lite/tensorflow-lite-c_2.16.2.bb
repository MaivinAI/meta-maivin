# Copyright 2020-2021 NXP
DESCRIPTION = "TensorFlow Lite C Library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = " \
    libeigen \
    abseil-cpp \
    flatbuffers-native \
    tensorflow-lite-host-tools-native \
    tensorflow-lite \
"
RDEPENDS:${PN} = "tensorflow-lite"

SRC_URI = "git://github.com/tensorflow/tensorflow.git;protocol=https;branch=r2.16"
SRCREV = "810f233968cec850915324948bbbc338c97cf57f"

S = "${WORKDIR}/git"

inherit cmake

OECMAKE_SOURCEPATH = "${S}/tensorflow/lite/c"

EXTRA_OECMAKE = "\
    -DCMAKE_SYSROOT=${PKG_CONFIG_SYSROOT_DIR} \
    -DTFLITE_HOST_TOOLS_DIR=${STAGING_BINDIR_NATIVE} \
    -DFETCHCONTENT_FULLY_DISCONNECTED=OFF \
    -DTFLITE_ENABLE_EXTERNAL_DELEGATE=ON \
    -DTFLITE_ENABLE_XNNPACK=ON \
"

do_configure[network] = "1"
do_configure:prepend() {
    export HTTP_PROXY=${http_proxy}
    export HTTPS_PROXY=${https_proxy}
    export http_proxy=${http_proxy}
    export https_proxy=${https_proxy}

    # There is no Fortran compiler in the toolchain, but bitbake sets this variable anyway
    # with unavailable binary.
    export FC=""

    rm -rf ${S}/tensorflow/lite/tools/cmake/modules/Findabsl.cmake
    rm -rf ${S}/tensorflow/lite/tools/cmake/modules/FindEigen3.cmake
}

do_install() {
    install -d ${D}/${libdir}
    install -m 0755 ${B}/libtensorflowlite_c.so ${D}/${libdir}/

    install -d ${D}${includedir}/tensorflow/lite/c
    install -m 644 ${S}/tensorflow/lite/c/c_api.h ${D}${includedir}/tensorflow/lite/c/
    install -m 644 ${S}/tensorflow/lite/c/common.h ${D}${includedir}/tensorflow/lite/c/
    install -m 644 ${S}/tensorflow/lite/c/c_api_experimental.h ${D}${includedir}/tensorflow/lite/c/
}

FILES_SOLIBSDEV = ""
FILES:${PN}-dev = "${includedir}"
FILES:${PN} += "${libdir}/libtensorflowlite_c.so"
