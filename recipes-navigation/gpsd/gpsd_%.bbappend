FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-ADIS-GPS-is-on-dev-ttymxc3.patch"

inherit features_check systemd

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "gpsd.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES:${PN} += "${systemd_system_unitdir}"