FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

MODEL_VERSION = "t-e2f-2"

SRC_URI:append = " \
    file://model.service \
    https://maivin.deepviewml.com/models/people/modelpack-people-${MODEL_VERSION}.tflite;name=people \
    https://maivin.deepviewml.com/models/people/modelpack-people-mask-${MODEL_VERSION}.tflite;name=people-mask \
    https://maivin.deepviewml.com/models/people/modelpack-people-detect-${MODEL_VERSION}.tflite;name=people-detect \
"
SRC_URI[people.sha256sum] = "db168a433407a3c94cb10048ed82dbd45fee607f3eb5d05a449e58e91d17e285"
SRC_URI[people-mask.sha256sum] = "253e3a7608b0e844a800de1ed7ace6b5bec47a2fe51eb330763a40af9dc89180"
SRC_URI[people-detect.sha256sum] = "d3300690781a436470eea52c5753402718ef25e5c8ce095d0a3f876dbeffab4f"

SYSTEMD_SERVICE:${PN} = "model.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/edgefirst-model.service
    install -m 0644 ${S}/model.service ${D}${systemd_system_unitdir}/model.service

    install -d ${D}${datadir}/edgefirst/model
    install -m 0644 ${S}/modelpack-people-${MODEL_VERSION}.tflite ${D}${datadir}/edgefirst/model/modelpack-people.tflite
    install -m 0644 ${S}/modelpack-people-mask-${MODEL_VERSION}.tflite ${D}${datadir}/edgefirst/model/modelpack-people-mask.tflite
    install -m 0644 ${S}/modelpack-people-detect-${MODEL_VERSION}.tflite ${D}${datadir}/edgefirst/model/modelpack-people-detect.tflite

    # Rename config file to short name
    mv ${D}${sysconfdir}/default/edgefirst-model ${D}${sysconfdir}/default/model

    ln -sf edgefirst-model ${D}${bindir}/model
}

FILES:${PN} += "${datadir}"
