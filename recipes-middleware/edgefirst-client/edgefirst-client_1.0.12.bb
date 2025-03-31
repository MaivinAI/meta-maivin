DESCRIPTION = "EdgeFirst Client CLI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/edgefirst-client/edgefirst_client-${PV}-cp38-abi3-manylinux_2_17_aarch64.manylinux2014_aarch64.whl \
    file://LICENSE \
"
SRC_URI[sha256sum] = "a2e1f036b9e30e220995b635da5f79aeb5e7bb02f0f886c8435d99ee382be8c6"

S = "${WORKDIR}"

inherit setuptools3 python3-dir

PACKAGES = "${PN}-python ${PN}"

DEPENDS = "python3 python3-pip-native"
RDEPENDS_${PN}-python = "python3"

do_configure:prepend() {
cat > ${S}/setup.py <<-EOF
from setuptools import setup

setup()
EOF
}

do_install:append () {
    install -d ${D}${bindir}    
    ln -sf edgefirst-client ${D}${bindir}/efc
    ln -sf edgefirst-client ${D}${bindir}/dve
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
FILES:${PN}-python += "${PYTHON_SITEPACKAGES_DIR}"
