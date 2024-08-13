DESCRIPTION = "Maivin Segmentation Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/detect/maivin-segment-${PV};name=segment \
    file://segment.service \
    file://segment.default \
    file://LICENSE \
"
SRC_URI[segment.sha256sum] = "075ce9f8f11d25bf6ce5e9942e51639c9e58c1c04d4afe6db2bd653694da4ed2"

DEPENDS = "vaal deepview-rt"
RDEPENDS_${PN} = "libvaal libdeepview-rt"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/segment.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/segment.default ${D}${sysconfdir}/default/segment

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-segment-${PV} ${D}${bindir}/segment
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "segment.service"
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${datadir}"
