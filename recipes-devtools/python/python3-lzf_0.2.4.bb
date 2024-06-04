SUMMARY = "C Extension for liblzf"
HOMEPAGE = "http://github.com/teepark/python-lzf"
AUTHOR = "Travis Parker <travis.parker@gmail.com>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a48661fe13b47fea6c4a8562ae73e41b"

SRC_URI = "https://files.pythonhosted.org/packages/e3/33/b8f67bbe695ccc39f868ae55378993a7bde1357a0567803a80467c8ce1a4/python-lzf-0.2.4.tar.gz"
SRC_URI[md5sum] = "67c2cb310821ead6efa4a0940f5e5737"
SRC_URI[sha256sum] = "d1420f1544e612ef1bb41ce0f1d14c2964b3444612f1468f85a886caff3615d1"

S = "${WORKDIR}/python-lzf-0.2.4"

RDEPENDS_${PN} = ""

inherit setuptools3
