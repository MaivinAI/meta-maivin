DESCRIPTION = "EdgeFirst LiDAR Publisher"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/lidarpub/edgefirst-lidarpub-${PV}-linux-${TARGET_ARCH} \
    file://lidarpub.service \
    file://lidarpub.default \
    file://LICENSE \
"
SRC_URI[sha256sum] = "30c427da507adce32c898144c21c4ecb111774697946f7691a61897e0acc635e"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/lidarpub.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/lidarpub.default ${D}${sysconfdir}/default/lidarpub

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-lidarpub-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/lidarpub
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "lidarpub.service"
SYSTEMD_AUTO_ENABLE = "disable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
