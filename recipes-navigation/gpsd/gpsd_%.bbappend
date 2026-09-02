FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://gpxlogger.service"
SRC_URI += "file://gpsd.default"
SRC_URI += "file://10-maivin-gps.conf"

inherit features_check systemd

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "gpsd.service"
SYSTEMD_AUTO_ENABLE = "enable"

RDEPENDS:${PN} = "gpscfg"

do_install:append() {
    install -m 0644 ${WORKDIR}/gpxlogger.service ${D}${systemd_system_unitdir}

    # Maivin defaults for the on-carrier GNSS receiver.  Written over the copy
    # the base recipe takes from the upstream Debian packaging directory --
    # /etc/default/gpsd is an update-alternatives symlink to this file, so the
    # content is substituted here rather than the link replaced.
    install -m 0644 ${WORKDIR}/gpsd.default ${D}${sysconfdir}/default/gpsd.default

    # Pin the UART rate before gpsd opens the port.
    install -d ${D}${systemd_system_unitdir}/gpsd.service.d
    install -m 0644 ${WORKDIR}/10-maivin-gps.conf ${D}${systemd_system_unitdir}/gpsd.service.d
}

FILES:${PN} += "${systemd_system_unitdir}"
