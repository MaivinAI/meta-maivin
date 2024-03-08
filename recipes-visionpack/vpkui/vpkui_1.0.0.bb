DESCRIPTION = "DeepView VisionPack UI Library"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit python3-dir systemd

SRC_URI = "https://deepviewml.com/vpkui/vpkui-${PV}-linux-armv8.zip;subdir=${S}"
SRC_URI += "file://vpkui.default"
SRC_URI += "file://segmentationui.service"
SRC_URI += "file://detectionui.service"
SRC_URI += "file://facedetectui.service"
SRC_URI += "file://faceblurui.service"
SRC_URI += "file://headposeui.service"
SRC_URI += "file://bodyposeui.service"
SRC_URI[sha256sum] = "43e5032cac9281fa73ac06a970579d7bd60990ca33425348c63465553a952246"

S = "${WORKDIR}/${PN}-${PV}"

PACKAGES = "${PN}-apps ${PN}"

DEPENDS = "deepview-rt vaal videostream libsoup-2.4"
RDEPENDS_${PN}-apps = "vpkui"
RDEPENDS_${PN} = "deepview-rt vaal videostream libsoup-2.4"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}${bindir}
    cp -rP  ${S}/bin/*_headless ${D}${bindir}

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/segmentationui.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/detectionui.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/facedetectui.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/faceblurui.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/headposeui.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/bodyposeui.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/vpkui.default ${D}${sysconfdir}/default/vpkui

    chown -R root:root "${D}"
}

FILES_SOLIBSDEV = ""

FILES:${PN}-apps += "${bindir}"
FILES:${PN}-apps += "${systemd_system_unitdir}"
FILES:${PN}-apps += "${sysconfdir}/default"
FILES:${PN} += "${libdir}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

INSANE_SKIP:${PN} += "dev-so"

BBCLASSEXTEND = "nativesdk"
COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"
