SUMMARY = "Python support for LMDB"
HOMEPAGE = "https://github.com/jnwatson/py-lmdb"

LICENSE = "OLDAP-2.8"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/OLDAP-2.8;md5=bb28ada4fbb5c3f52c233899b2e410a5"

PYPI_PACKAGE = "lmdb"

inherit pypi setuptools3 python_setuptools_build_meta

SRC_URI[sha256sum] = "d28e3fa59935ff688858760ec52f202ecb8c1089a3f68d1f162ea3078d151e73"

RDEPENDS:${PN} += "lmdb"
DEPENDS += "python3 python3-setuptools-native lmdb"

BBCLASSEXTEND = "native nativesdk"
