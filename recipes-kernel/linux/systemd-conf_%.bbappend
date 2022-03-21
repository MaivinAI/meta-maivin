FILESEXTRAPATHS_prepend := "${THISDIR}/linux-toradex:"

# to remove the system default watchdog
SRC_URI += "file://10-systemd-conf.conf"

# Adding this custom 10-systemd-conf.conf file, that differs from the default one only by removing the watchdog line,
# will make the system not use the /dev/watchdog device, so a custom application can use it
do_install_append() {
    install -d ${D}${sysconfdir}/systemd/system.conf.d/
    install -m 0755 ${WORKDIR}/10-systemd-conf.conf ${D}${sysconfdir}/systemd/system.conf.d/10-systemd-conf.conf
}

FILES_${PN} += "${sysconfdir}/systemd/"
FILES_${PN} += "${sysconfdir}/systemd/system.conf.d"
FILES_${PN} += "${sysconfdir}/systemd/system.conf.d/10-systemd-conf.conf"