SUMMARY = "Linux Driver User Space DMA"
DESCRIPTION = "${SUMMARY}"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=39bba7d2cf0ba1036f2a6e2be52fe3f0"

inherit module

SRC_URI = "git://github.com/MaivinAI/kernel-module-uiodma.git;protocol=https;branch=main"
SRCREV = "417cc5d8e0096c2069bb0ddc1a31a4b4879ce9bd"

S = "${WORKDIR}/git"

RPROVIDES:${PN} += "kernel-module-uiodma"
