DESCRIPTION = "Maivin Web UI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f5cddf357882b5f52de019ee365c75a"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "https://github.com/MaivinAI/webui/archive/refs/tags/${PV}.zip"
SRC_URI[sha256sum] = "41419c4fd6b6904e41770561aeed9817c3d9d1f43a6438add8c48dc8317fcdbe"

S = "${WORKDIR}/webui-${PV}"

do_install () {
    install -d ${D}${datadir}
    cp -r ${S}/src ${D}${datadir}/webui
}

FILES:${PN} += "${datadir}"
