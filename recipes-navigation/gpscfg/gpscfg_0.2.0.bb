DESCRIPTION = "GPS Configuration Tool"
LICENSE = "AGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0e8e3a5b1effdf50d66fe427bcc8978f"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "gitsm://github.com/MaivinAI/gpscfg.git;protocol=https;branch=main"

include gpscfg.inc

SRCREV = "3f66680021cc5a1bbc7e68d42431bf9e4b06d5ab"

S = "${WORKDIR}/git"

DEPENDS = "udev"

inherit pkgconfig cargo

# do_install:append () {
#     install -d ${D}${systemd_system_unitdir}
#     install -m 0644 ${WORKDIR}/camerapose.service ${D}${systemd_system_unitdir}
# }

# FILES:${PN} += "${bindir}"
# FILES:${PN} += "${systemd_system_unitdir}"
