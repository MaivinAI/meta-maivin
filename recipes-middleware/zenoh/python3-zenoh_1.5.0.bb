SUMMARY = "Python support for Zenoh"
HOMEPAGE = "https://github.com/eclipse-zenoh/zenoh-python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
SRC_URI = "https://files.pythonhosted.org/packages/35/dc/c4f16faef830e221a1fd3eb81205abdb75324389c914408029706ba60fe2/eclipse_zenoh-${PV}-cp38-abi3-manylinux_2_28_aarch64.whl"
SRC_URI[sha256sum] = "b672a5fd266e9fe04d9afdb3f869006ea331aa4d0f5f9fd1f53955eb889f5fad"

inherit python3-dir

DEPENDS = "python3 python3-pip-native"
RDEPENDS_${PN}-python = "python3"

do_install() {
    mkdir -p ${D}${PYTHON_SITEPACKAGES_DIR}
    unzip ${WORKDIR}/eclipse_zenoh-${PV}-cp38-abi3-manylinux_2_28_aarch64.whl -d ${D}${PYTHON_SITEPACKAGES_DIR}
}

INSANE_SKIP:${PN} += "ldflags"

FILES:${PN} = "${PYTHON_SITEPACKAGES_DIR}"
