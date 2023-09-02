DESCRIPTION = "Update Overlays Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://update-overlays.sh;md5=5cddda635a401e60231c66b5fc840c93"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    file://update-overlays.sh \
    file://update-overlays.service \
"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}/${bindir}
    install -m 0755 ${WORKDIR}/update-overlays.sh ${D}/${bindir}/update-overlays

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/update-overlays.service ${D}${systemd_system_unitdir}
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "update-overlays.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES:${PN} += "${systemd_system_unitdir}"
