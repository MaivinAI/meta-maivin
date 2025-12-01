SUMMARY = "Python support for Zenoh"
HOMEPAGE = "https://github.com/eclipse-zenoh/zenoh-python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
SRC_URI = "https://files.pythonhosted.org/packages/29/fe/c5b2e65e0c2800b4e1a02b388e0ddf7c3b71f478757bb6a294c021d49e84/eclipse_zenoh-${PV}-cp39-abi3-manylinux_2_28_aarch64.whl"
SRC_URI[sha256sum] = "e7f9bf3849567631caa3bdeecf2edce66461b1ae511f28dfafea6089022d02e1"

inherit python3-dir

DEPENDS = "python3 python3-pip-native"
RDEPENDS_${PN}-python = "python3"

do_install() {
    mkdir -p ${D}${PYTHON_SITEPACKAGES_DIR}
    unzip ${WORKDIR}/eclipse_zenoh-${PV}-cp39-abi3-manylinux_2_28_aarch64.whl -d ${D}${PYTHON_SITEPACKAGES_DIR}
}

INSANE_SKIP:${PN} += "ldflags"

FILES:${PN} = "${PYTHON_SITEPACKAGES_DIR}"
