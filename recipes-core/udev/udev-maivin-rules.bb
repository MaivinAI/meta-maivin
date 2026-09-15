DESCRIPTION = "udev rules for Maivin AI Vision Starter Kit"
LICENSE = "AGPL-3.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/AGPL-3.0-only;md5=73f1eb20517c55bf9493b7dd6e480788"

SRC_URI = "\
    file://maivin.rules \
    file://carrier_temp.sh \
    file://carrier_humidity.sh \
    file://carrier_eeprom.sh \
    file://reario_eeprom.sh \
    file://maivin-sd-format \
    file://maivin-sd-format.service \
    file://media-DATA.mount \
"

S = "${@d.getVar('UNPACKDIR') or d.getVar('WORKDIR')}"

inherit features_check systemd

do_install () {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -d ${D}${sysconfdir}/udev/scripts
    install -d ${D}${libexecdir}
    install -d ${D}${systemd_system_unitdir}

    install -m 0644 ${S}/maivin.rules ${D}${sysconfdir}/udev/rules.d/

    install -m 0755 ${S}/carrier_temp.sh ${D}${sysconfdir}/udev/scripts/
    install -m 0755 ${S}/carrier_humidity.sh ${D}${sysconfdir}/udev/scripts/
    install -m 0755 ${S}/carrier_eeprom.sh ${D}${sysconfdir}/udev/scripts/
    install -m 0755 ${S}/reario_eeprom.sh ${D}${sysconfdir}/udev/scripts/

    install -m 0755 ${S}/maivin-sd-format ${D}${libexecdir}/maivin-sd-format
    install -m 0644 ${S}/maivin-sd-format.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${S}/media-DATA.mount ${D}${systemd_system_unitdir}/
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "maivin-sd-format.service media-DATA.mount"
SYSTEMD_AUTO_ENABLE = "enable"

RDEPENDS:${PN} += "e2fsprogs parted util-linux"

FILES:${PN} += "${libexecdir}/maivin-sd-format"
FILES:${PN} += "${systemd_system_unitdir}"
