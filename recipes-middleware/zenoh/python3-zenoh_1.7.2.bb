SUMMARY = "Python support for Zenoh"
HOMEPAGE = "https://github.com/eclipse-zenoh/zenoh-python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
SRC_URI = "https://files.pythonhosted.org/packages/8d/4a/ad58417a73271e1a667b8bec1f6d42b500c0966c5e48a10c321e8c3f4634/eclipse_zenoh-${PV}-cp39-abi3-manylinux_2_28_aarch64.whl"
SRC_URI[sha256sum] = "07cf23f7de43a9008f9dd81bf5028a0459e43227087bc7838a0ba36c1eb232e7"

inherit python3-dir

DEPENDS = "python3 python3-pip-native"
RDEPENDS_${PN}-python = "python3"

do_install() {
    mkdir -p ${D}${PYTHON_SITEPACKAGES_DIR}
    unzip ${WORKDIR}/eclipse_zenoh-${PV}-cp39-abi3-manylinux_2_28_aarch64.whl -d ${D}${PYTHON_SITEPACKAGES_DIR}
}

INSANE_SKIP:${PN} += "ldflags"

FILES:${PN} = "${PYTHON_SITEPACKAGES_DIR}"
