
SUMMARY = "Read and write PCL .pcd files in python"
HOMEPAGE = "None"
AUTHOR = "None <urasakikeisuke <keisuke.urasaki@map4.jp>>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=a45b667fd241be0c9b43402e50458b4d"

SRC_URI = "https://files.pythonhosted.org/packages/a1/bb/f26f8dca750f10e38fa35d1a4064406b12ae8fc525eb1ac4ee651e889c23/pypcd4-1.0.0.tar.gz"
SRC_URI[md5sum] = "57ecad383aeb0cdd153192fb7c9ef149"
SRC_URI[sha256sum] = "b21cf1e1a790c47615d261959c95c8a1c85665eaa75da9690a7066b4db047578"

S = "${WORKDIR}/pypcd4-1.0.0"

RDEPENDS_${PN} = "python3-numpy python3-lzf"

inherit setuptools3

do_configure:prepend() {
cat > ${S}/setup.py <<-EOF
from setuptools import setup

setup(
    name="${PYPI_PACKAGE}",
    version="${PV}",
    license="${LICENSE}",
)
EOF
}
