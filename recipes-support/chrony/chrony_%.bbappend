FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://chrony-wait-sync"
SRC_URI += "file://10-chrony-waitsync.conf"
SRC_URI += "file://gpsd.conf"

inherit features_check systemd

do_install:append() {
    # Override upstream's systemd-time-wait-sync.service (which polls
    # timedatectl/the kernel NTP-sync flag) to instead run a bounded
    # `chronyc waitsync` wait -- see chrony-wait-sync for why.
    install -d ${D}${systemd_unitdir}
    install -m 0755 ${WORKDIR}/chrony-wait-sync ${D}${systemd_unitdir}

    install -d ${D}${systemd_system_unitdir}/systemd-time-wait-sync.service.d
    install -m 0644 ${WORKDIR}/10-chrony-waitsync.conf ${D}${systemd_system_unitdir}/systemd-time-wait-sync.service.d

    # GPSD refclock drop-in for chrony
    install -d ${D}${sysconfdir}/chrony/conf.d
    install -m 0644 ${WORKDIR}/gpsd.conf ${D}${sysconfdir}/chrony/conf.d

    # The stock chrony.conf has no include directive, so the drop-in above was
    # never read.  Add one, and drop the inline NMEA refclock it supersedes --
    # keeping both would give chrony two refclocks on the same SHM segment.
    sed -i -e '/^refclock SHM 0/d' ${D}${sysconfdir}/chrony.conf
    printf '\n# Maivin drop-ins (GNSS refclocks).\ninclude %s/chrony/conf.d/*.conf\n' \
        "${sysconfdir}" >> ${D}${sysconfdir}/chrony.conf
}

REQUIRED_DISTRO_FEATURES = "systemd"

FILES:${PN} += "${systemd_unitdir}"
FILES:${PN} += "${sysconfdir}/chrony"
