SUMMARY = "Python CDR encoder and decoder"
HOMEPAGE = "https://pypi.org/project/pycdr2/"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

PYPI_PACKAGE = "pycdr2"

inherit pypi setuptools3 python_setuptools_build_meta

SRC_URI[sha256sum] = "e2b34aff7e9a536de7b4a2e367d154460d19b6319c1d1d5e068218b0dd539f7c"

DEPENDS += "python3 python3-setuptools-native"

BBCLASSEXTEND = "native nativesdk"
