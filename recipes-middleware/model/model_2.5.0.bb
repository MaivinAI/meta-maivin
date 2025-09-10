DESCRIPTION = "EdgeFirst Model Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

MODEL_VERSION = "t-e2f"

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
SRC_URI[model.sha256sum] = "b59c3bac8e5f176a214417ae5978c461bffd269a9b8a7bc196caa943f4ac1807"
SRC_URI[people.sha256sum] = "202f23f6c2e141994ab270769f1d7cfe2eeb679fb81708665cf2c997839ddf08"
SRC_URI[people-mask.sha256sum] = "b34bd9a079b32b56a8da3893662438830eb75e26a903758522753cf946367f1c"
SRC_URI[people-detect.sha256sum] = "23c4ff7cc9417148a19e2991f95990ff1f23879dfc2c024e03f82737ae83dc57"

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
