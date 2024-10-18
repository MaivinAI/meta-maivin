SUMMARY = "MDIO Netlink Driver to enable the MDIO Tools"
DESCRIPTION = "${SUMMARY}"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit module

SRC_URI = "git://github.com/wkz/mdio-tools.git;protocol=https;branch=master"
SRCREV = "f74eaf38dbda441df4fcaeb21ca4465957953a2f"

S = "${WORKDIR}/git"

MODULES_INSTALL_TARGET = "install"
MODULES_MODULE_SYMVERS_LOCATION = "kernel"

EXTRA_OEMAKE += "KDIR=${STAGING_KERNEL_DIR}"

do_compile:prepend() {
    cd kernel
}

do_install:prepend() {
    cd kernel
}

RPROVIDES:${PN} += "kernel-module-mdio-netlink"
