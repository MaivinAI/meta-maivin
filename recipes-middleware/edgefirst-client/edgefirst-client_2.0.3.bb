DESCRIPTION = "EdgeFirst Client CLI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/edgefirst-client/edgefirst_client-${PV}-cp38-abi3-manylinux_2_17_aarch64.manylinux2014_aarch64.whl \
    file://LICENSE \
"

SRC_URI[sha256sum] = "41651deb9bfb7cfb69b781ca852527d89a0e23d8aec684dd4dcd17c8432529c0"

S = "${WORKDIR}"

inherit python3-dir

PACKAGES = "${PN}-python ${PN}"

DEPENDS = "python3 python3-pip-native"
RDEPENDS_${PN}-python = "python3"

do_install () {
    install -d ${D}${bindir}
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    
    unzip ${WORKDIR}/edgefirst_client-${PV}-cp38-abi3-manylinux_2_17_aarch64.manylinux2014_aarch64.whl -d ${D}${PYTHON_SITEPACKAGES_DIR}
    mv ${D}${PYTHON_SITEPACKAGES_DIR}/edgefirst_client-${PV}.data/scripts/edgefirst-client ${D}${bindir}
    rm -rf ${D}${PYTHON_SITEPACKAGES_DIR}/edgefirst_client-${PV}.data
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
FILES:${PN}-python += "${PYTHON_SITEPACKAGES_DIR}"
