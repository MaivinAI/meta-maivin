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

do_compile:prepend() {
    export KDIR=/home/sebastien/maivin/yocto/build/tmp/work-shared/verdin-imx8mp/kernel-source
    cd kernel
}

do_install:prepend() {
    export KDIR=/home/sebastien/maivin/yocto/build/tmp/work-shared/verdin-imx8mp/kernel-source
    cd kernel
}

RPROVIDES:${PN} += "kernel-module-mdio-netlink"
