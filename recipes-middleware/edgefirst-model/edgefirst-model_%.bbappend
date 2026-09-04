FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://model.service"

SYSTEMD_SERVICE:${PN} = "model.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-model.service
    install -m 0644 ${S}/model.service ${D}${systemd_system_unitdir}/model.service

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-model ${D}${sysconfdir}/default/model

    ln -sf edgefirst-model ${D}${bindir}/model
}
