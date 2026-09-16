# DATA SD card is owned exclusively by var-rootdirs-media-DATA.mount; udisks2
# must never race it (see MVN2-202). Appended, not installed, so this
# composes with meta-toradex-security's tdx-encrypted override on the same
# file rather than clobbering it.
do_install:append() {
    install -d ${D}${sysconfdir}/usermount
    echo /dev/mmcblk1p1 >> ${D}${sysconfdir}/usermount/ignorelist
}

FILES:${PN} += "${sysconfdir}/usermount"
