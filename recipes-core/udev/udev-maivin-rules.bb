DESCRIPTION = "udev rules for Maivin AI Vision Starter Kit"
LICENSE = "AGPL-3.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/AGPL-3.0-only;md5=73f1eb20517c55bf9493b7dd6e480788"

SRC_URI = "\
    file://maivin.rules \
    file://carrier_temp.sh \
    file://carrier_eeprom.sh \
    file://reario_eeprom.sh \
"

do_install () {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -d ${D}${sysconfdir}/udev/scripts

    install -m 0644 ${WORKDIR}/maivin.rules ${D}${sysconfdir}/udev/rules.d/

    install -m 0755 ${WORKDIR}/carrier_temp.sh ${D}${sysconfdir}/udev/scripts/
    install -m 0755 ${WORKDIR}/carrier_eeprom.sh ${D}${sysconfdir}/udev/scripts/
    install -m 0755 ${WORKDIR}/reario_eeprom.sh ${D}${sysconfdir}/udev/scripts/
}
