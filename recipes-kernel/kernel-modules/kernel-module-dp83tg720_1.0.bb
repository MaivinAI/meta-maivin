SUMMARY = "Linux Driver for the TI DP83TG720 Automotive Ethernet PHY"
DESCRIPTION = "${SUMMARY}"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=39bba7d2cf0ba1036f2a6e2be52fe3f0"

inherit module

SRC_URI = "git://github.com/MaivinAI/kernel-module-dp83tg720.git;protocol=https;branch=main"
SRCREV = "c88ee59d71e14cf4fad3be3ab5add0ff91705da5"

S = "${WORKDIR}/git"

RPROVIDES:${PN} += "kernel-module-dp83tg720"
