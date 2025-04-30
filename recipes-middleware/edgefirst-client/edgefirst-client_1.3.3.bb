DESCRIPTION = "EdgeFirst Client CLI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/edgefirst-client/edgefirst-client-${PV}-linux-arm64;name=app \
    https://maivin.deepviewml.com/services/edgefirst-client/edgefirst_client-${PV}-cp38-abi3-manylinux_2_17_aarch64.manylinux2014_aarch64.whl;name=whl \
    file://LICENSE \
"

SRC_URI[app.sha256sum] = "e43ceeac62420c8fb0a548558f12f42549b5ec146ee08c8372fbdbae7e02509a"
SRC_URI[whl.sha256sum] = "a2af1b05c8ccdc6400efa4510dabca36fd4cbed13ddd26b81e00fe32fbbc41ef"

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
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${bindir}"
FILES:${PN}-python += "${PYTHON_SITEPACKAGES_DIR}"
