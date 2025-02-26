SUMMARY = "Linux Driver User Space DMA"
DESCRIPTION = "${SUMMARY}"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=39bba7d2cf0ba1036f2a6e2be52fe3f0"

inherit module

SRC_URI = "git://github.com/MaivinAI/kernel-module-uiodma.git;protocol=https;branch=main"
SRCREV = "bd19623c1cd121649adc65cb249b9dea6e58a0a7"

S = "${WORKDIR}/git"

RPROVIDES:${PN} += "kernel-module-uiodma"
