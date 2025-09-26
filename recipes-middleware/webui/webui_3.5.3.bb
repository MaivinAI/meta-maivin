DESCRIPTION = "Maivin Web UI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f5cddf357882b5f52de019ee365c75a"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "https://github.com/MaivinAI/webui/archive/refs/tags/${PV}.zip"
SRC_URI[sha256sum] = "3236e78027f0265a476c2add70ab212ad54deb3f6df3845aa378d8aa6cf34ff4"

S = "${WORKDIR}/webui-${PV}"

do_install () {
    install -d ${D}${datadir}
    cp -r ${S}/src ${D}${datadir}/webui
}

FILES:${PN} += "${datadir}"
