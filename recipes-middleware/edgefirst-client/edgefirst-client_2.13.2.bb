DESCRIPTION = "EdgeFirst Client CLI"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "https://github.com/EdgeFirstAI/client/releases/download/v${PV}/edgefirst_client-${PV}-cp38-abi3-manylinux_2_17_${TARGET_ARCH}.manylinux2014_${TARGET_ARCH}.whl"
SRC_URI[sha256sum] = "ab629ca6408bd83ed0fe60973917291af9e6f807ace779eea991ecb4deed164a"

S = "${WORKDIR}"

inherit python3-dir

PACKAGES = "${PN}-python ${PN}"

DEPENDS = "python3 python3-pip-native"
RDEPENDS:${PN}-python = "python3"

do_install () {
    install -d ${D}${bindir}
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}

    if [ "${UNPACKDIR}" != "" ]; then
        unzip ${UNPACKDIR}/edgefirst_client-${PV}-cp38-abi3-manylinux_2_17_${TARGET_ARCH}.manylinux2014_${TARGET_ARCH}.whl -d ${D}${PYTHON_SITEPACKAGES_DIR}
    else
        unzip ${WORKDIR}/edgefirst_client-${PV}-cp38-abi3-manylinux_2_17_${TARGET_ARCH}.manylinux2014_${TARGET_ARCH}.whl -d ${D}${PYTHON_SITEPACKAGES_DIR}
    fi
    mv ${D}${PYTHON_SITEPACKAGES_DIR}/edgefirst_client-${PV}.data/scripts/edgefirst-client ${D}${bindir}
    rm -rf ${D}${PYTHON_SITEPACKAGES_DIR}/edgefirst_client-${PV}.data
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
FILES:${PN}-python += "${PYTHON_SITEPACKAGES_DIR}"
