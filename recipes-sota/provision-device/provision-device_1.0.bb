DESCRIPTION = "Torizon OTA Provision Device"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://provision-device.sh;md5=2f9c33b2c7828ff77aaa88a7816774e0"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "file://provision-device.sh"

S = "${WORKDIR}"

do_install() {
    install -m 0755 -d ${D}${bindir}
    install -m 0755 ${S}/provision-device.sh ${D}/${bindir}
}

FILES:${PN} += "${bindir}"
