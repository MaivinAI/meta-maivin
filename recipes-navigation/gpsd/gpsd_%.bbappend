FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://gpxlogger.service"
SRC_URI += "file://ADIS-GPS-is-on-dev-ttymxc3.patch"
SRC_URI += "file://Force-gpsd-to-use-38400-8N1-serial-settings-to-avoid.patch"
SRC_URI += "file://Configure-target-baudrate-on-device-before-launching.patch"

inherit features_check systemd

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "gpsd.service"
SYSTEMD_AUTO_ENABLE = "enable"

RDEPENDS:${PN} = "gpscfg"

do_install:append() {
    install -m 0644 ${WORKDIR}/gpxlogger.service ${D}${systemd_system_unitdir}
}

FILES:${PN} += "${systemd_system_unitdir}"
