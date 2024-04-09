SUMMARY = "libelfin"
DESCRIPTION = "Libelfin is a from-scratch C++11 library for reading ELF binaries and DWARFv4 debug information."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ca5398b9660130906bd321728acb697"

SRC_URI = "git://github.com/antoyo/libelfin;protocol=https;branch=master"
SRCREV = "946dde57e5caef8297b9339f3a7971401d540840"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "DESTDIR=${D} PREFIX=${prefix} CC='${CC}' CFLAGS='${CFLAGS}'"

do_install() {
    oe_runmake install
}

FILES_SOLIBSDEV = ""

FILES:${PN} += "${libdir}/libelf++.so"
FILES:${PN} += "${libdir}/libdwarf++.so"
