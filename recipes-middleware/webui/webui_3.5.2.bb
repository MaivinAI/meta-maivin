DESCRIPTION = "Maivin Web UI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f5cddf357882b5f52de019ee365c75a"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "https://github.com/MaivinAI/webui/archive/refs/tags/${PV}.zip"
SRC_URI[sha256sum] = "74b64dacb00440fadc0e15ab559117027231ca05674fc3dbf02f9687e48a9f5b"

S = "${WORKDIR}/webui-${PV}"

do_install () {
    install -d ${D}${datadir}
    cp -r ${S}/src ${D}${datadir}/webui
}

FILES:${PN} += "${datadir}"
