# DATA SD card, formatted by maivin-sd-format / maivin-provision --format-sd.
MAIVIN_DATA_FSTAB = "LABEL=DATA           /media/DATA          ext4       defaults,nofail,x-systemd.device-timeout=5s  0  0"

do_install:append() {
    sed -i -e '/uncomment this if your device has a SD/d' -e '\#^\#*/dev/mmcblk0p1#d' ${D}${sysconfdir}/fstab
    echo "${MAIVIN_DATA_FSTAB}" >> ${D}${sysconfdir}/fstab
}
