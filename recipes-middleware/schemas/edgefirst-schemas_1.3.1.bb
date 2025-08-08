SUMMARY = "EdgeFirst Schemas for Python"
HOMEPAGE = "https://github.com/EdgeFirstAI/schemas"

LICENSE = "AGPL-3.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/AGPL-3.0-only;md5=73f1eb20517c55bf9493b7dd6e480788"
SRC_URI = "https://files.pythonhosted.org/packages/e9/b5/17b19d04d5fd3ee93c067f092b4f62e04a2a21bd1a2535a162436ae77a26/edgefirst_schemas-${PV}-py3-none-any.whl"
SRC_URI[sha256sum] = "862b5326e61a6f1bf40044c285f594ec41cf101e175fe9e8ce7b338608c3730c"

inherit python3-dir

DEPENDS = "python3 python3-pip-native"
RDEPENDS_${PN}-python = "python3"

do_install() {
    mkdir -p ${D}${PYTHON_SITEPACKAGES_DIR}
    unzip ${WORKDIR}/edgefirst_schemas-${PV}-py3-none-any.whl -d ${D}${PYTHON_SITEPACKAGES_DIR}
}

INSANE_SKIP:${PN} += "ldflags"

FILES:${PN} = "${PYTHON_SITEPACKAGES_DIR}"
