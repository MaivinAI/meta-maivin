DESCRIPTION = "EdgeFirst Client CLI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/edgefirst-client/edgefirst-client-${PV}-linux-arm64;name=app \
    https://maivin.deepviewml.com/services/edgefirst-client/edgefirst-client-${PV}-cp38-abi3-manylinux_2_17_aarch64.manylinux2014_aarch64.whl;name=whl \
    file://LICENSE \
"

SRC_URI[app.sha256sum] = "95ae897e3486283005c7c2b354af335acaeca3d606ee07157b451ce3c27257f7"
SRC_URI[whl.sha256sum] = "754ed2deda33f55150ae79c8c0e6cc074d0263a81fe1625e25af5899f6b16ae0"

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
    install -m 0755 ${WORKDIR}/edgefirst-client-${PV}-linux-arm64 ${D}${bindir}/edgefirst-client
    ln -sf edgefirst-client ${D}${bindir}/efc
    ln -sf edgefirst-client ${D}${bindir}/dve
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
FILES:${PN}-python += "${PYTHON_SITEPACKAGES_DIR}"
