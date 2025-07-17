DESCRIPTION = "Maivin Web UI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f5cddf357882b5f52de019ee365c75a"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "https://github.com/MaivinAI/webui/archive/refs/tags/${PV}.zip"
SRC_URI[sha256sum] = "8d093ecce018e88311cf0c9981566109950f195f991b8696d42880e93fb9087e"

S = "${WORKDIR}/webui-${PV}"

do_install () {
    install -d ${D}${datadir}
    cp -r ${S}/src ${D}${datadir}/webui
}

FILES:${PN} += "${datadir}"
