
SUMMARY = "Fundamental package for array computing in Python"
HOMEPAGE = "https://numpy.org"
AUTHOR = "Travis E. Oliphant et al. <>"
LICENSE = ""
LIC_FILES_CHKSUM = "file://LICENSES_bundled.txt;md5=fb2175e9d0dc803f2c2e311c552b853c"

SRC_URI = "https://files.pythonhosted.org/packages/65/6e/09db70a523a96d25e115e71cc56a6f9031e7b8cd166c1ac8438307c14058/numpy-1.26.4.tar.gz"
SRC_URI[md5sum] = "19550cbe7bedd96a928da9d4ad69509d"
SRC_URI[sha256sum] = "2a02aba9ed12e4ac4eb3ea9421c420301a0c6460d9830d74a9df87efa4912010"

S = "${WORKDIR}/numpy-1.26.4"

RDEPENDS_${PN} = ""

inherit setuptools3
