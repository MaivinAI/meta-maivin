DESCRIPTION = "GPS Configuration Tool"
LICENSE = "AGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0e8e3a5b1effdf50d66fe427bcc8978f"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "gitsm://github.com/MaivinAI/gpscfg.git;protocol=ssh;branch=main"

include gpscfg.inc

SRCREV = "fcdd7a5078b3db0eb4dc5f0de9f96302aaeed11b"

S = "${WORKDIR}/git"

DEPENDS = "udev"

inherit pkgconfig cargo

# do_install:append () {
#     install -d ${D}${systemd_system_unitdir}
#     install -m 0644 ${WORKDIR}/camerapose.service ${D}${systemd_system_unitdir}
# }

# FILES:${PN} += "${bindir}"
# FILES:${PN} += "${systemd_system_unitdir}"
