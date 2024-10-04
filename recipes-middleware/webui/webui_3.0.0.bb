DESCRIPTION = "Maivin Web UI"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f5cddf357882b5f52de019ee365c75a"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "git://github.com/MaivinAI/webui.git;protocol=ssh;branch=main"
SRCREV = "f00818e32017db761b25d47c09db25e153da93c3"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}${datadir}
    cp -r ${S}/src ${D}${datadir}/webui
}

FILES:${PN} += "${datadir}"