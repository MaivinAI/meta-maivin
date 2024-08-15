DESCRIPTION = "Maivin Segmentation Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/detect/maivin-segment-${PV};name=segment \
    https://github.com/DeepViewML/peoplesegment/releases/download/1.0/peoplesegment.rtm;name=peoplesegment \
    file://segment.service \
    file://segment.default \
    file://LICENSE \
"
SRC_URI[segment.sha256sum] = "075ce9f8f11d25bf6ce5e9942e51639c9e58c1c04d4afe6db2bd653694da4ed2"
SRC_URI[peoplesegment.sha256sum] = "2f37bfd00e9b14fd6a5379db87c6f4a0c413b30fa0b3c8da78657a2b2089fc1e"

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

    install -d ${D}${datadir}/
    install -m 0644 ${WORKDIR}/peoplesegment.rtm ${D}${datadir}/segment
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "segment.service"
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${datadir}"
