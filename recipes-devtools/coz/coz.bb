SUMMARY = "coz"
DESCRIPTION = "Coz is a profiler for native code (C/C++/Rust) that unlocks optimization opportunities missed by traditional profilers."

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=a530ef2e6c63d750d7ed0d56521467f0"

SRC_URI = "git://github.com/plasma-umass/coz;protocol=https;branch=master"
SRCREV = "4659e50e4237345cbd70459a14ee64699cee2893"

S = "${WORKDIR}/git"

DEPENDS = "libelfin"

inherit cmake pkgconfig

do_install:append() {
    rm ${D}/usr/licenses/LICENSE.md
    rmdir ${D}/usr/licenses
}

FILES_SOLIBSDEV = ""

FILES:${PN} += "${libdir}/libcoz.so"
