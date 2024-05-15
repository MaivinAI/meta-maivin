DESCRIPTION = "Maivin EdgeFirst Services Schemas"
LICENSE = "AGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4ae09d45eac4aa08d013b5f2e01c67f6"

SRC_URI += "git://github.com/MaivinAI/schemas.git;protocol=https;branch=main"
SRCREV = "6f2d30a3c291b9dd324a5fe3b67ddabd946c4fff"
S = "${WORKDIR}/git"

do_install() {
	install -d ${D}${datadir}/edgefirst
	cp -r ${S}/schemas ${D}${datadir}/edgefirst
}

FILES:${PN} += "${datadir}/edgefirst/schemas"
