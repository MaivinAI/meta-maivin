DESCRIPTION = "EdgeFirst Model Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

MODEL_VERSION = "t-e2f-2"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/model/edgefirst-model-${PV}-linux-${TARGET_ARCH};name=model \
    https://maivin.deepviewml.com/models/people/modelpack-people-${MODEL_VERSION}.tflite;name=people \
    https://maivin.deepviewml.com/models/people/modelpack-people-mask-${MODEL_VERSION}.tflite;name=people-mask \
    https://maivin.deepviewml.com/models/people/modelpack-people-detect-${MODEL_VERSION}.tflite;name=people-detect \
    file://model.service \
    file://model.default \
    file://LICENSE \
"
SRC_URI[model.sha256sum] = "9770cee2674cad681b49857523902dc9f5d9ad2c23956c3d183d087da5f43c11"
SRC_URI[people.sha256sum] = "db168a433407a3c94cb10048ed82dbd45fee607f3eb5d05a449e58e91d17e285"
SRC_URI[people-mask.sha256sum] = "253e3a7608b0e844a800de1ed7ace6b5bec47a2fe51eb330763a40af9dc89180"
SRC_URI[people-detect.sha256sum] = "d3300690781a436470eea52c5753402718ef25e5c8ce095d0a3f876dbeffab4f"

RDEPENDS_${PN} = "tflite"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/model.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/model.default ${D}${sysconfdir}/default/model

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/edgefirst-model-${PV}-linux-${TARGET_ARCH} ${D}${bindir}/model

    install -d ${D}${datadir}/model
    install -m 0644 ${WORKDIR}/modelpack-people-${MODEL_VERSION}.tflite ${D}${datadir}/model/modelpack-people.tflite
    install -m 0644 ${WORKDIR}/modelpack-people-mask-${MODEL_VERSION}.tflite ${D}${datadir}/model/modelpack-people-mask.tflite
    install -m 0644 ${WORKDIR}/modelpack-people-detect-${MODEL_VERSION}.tflite ${D}${datadir}/model/modelpack-people-detect.tflite
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "model.service"
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${datadir}"
