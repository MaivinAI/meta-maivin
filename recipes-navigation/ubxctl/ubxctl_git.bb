DESCRIPTION = "U-Blox Configuration Tool"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=8c00b96695a5cfc747c020f961ac8229"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "git://github.com/MaivinAI/ublox.git;protocol=ssh;branch=feature/TOP2-259-ubxctl"

include ubxctl.inc

SRCREV = "773a18a30a2e8918e18ec95ee248839ffe6600fc"
S = "${WORKDIR}/git"
CARGO_SRC_DIR = "ubxctl"

DEPENDS = "udev"

inherit pkgconfig cargo

# do_install:append () {
#     install -d ${D}${systemd_system_unitdir}
#     install -m 0644 ${WORKDIR}/camerapose.service ${D}${systemd_system_unitdir}
# }

# FILES:${PN} += "${bindir}"
# FILES:${PN} += "${systemd_system_unitdir}"
