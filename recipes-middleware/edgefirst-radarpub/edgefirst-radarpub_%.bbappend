FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://radarpub.service \
"

SYSTEMD_SERVICE:${PN} = "radarpub.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-radarpub.service
    install -m 0644 ${S}/radarpub.service ${D}${systemd_system_unitdir}/radarpub.service

    # Maivin default: low/ultra-short range, which the shipped example model
    # is trained for.
    mv ${D}${sysconfdir}/default/edgefirst-radarpub ${D}${sysconfdir}/default/radarpub
    for kv in CENTER_FREQUENCY=low FREQUENCY_SWEEP=ultra-short; do
        grep -q "^${kv%%=*}=" ${D}${sysconfdir}/default/radarpub || \
            bbfatal "radarpub.default no longer defines ${kv%%=*}"
        sed -i "s/^${kv%%=*}=.*/${kv%%=*}=\"${kv#*=}\"/" ${D}${sysconfdir}/default/radarpub
    done

    ln -sf edgefirst-radarpub ${D}${bindir}/radarpub
}
