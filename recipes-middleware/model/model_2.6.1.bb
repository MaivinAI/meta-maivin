DESCRIPTION = "EdgeFirst Model Service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${PN}-LICENSE;md5=3929fde384c07d35ed0d6f0c925f2a12"

MODEL_VERSION = "t-e2f-2"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://github.com/EdgeFirstAI/model/releases/download/v${PV}/edgefirst-model-linux-${TARGET_ARCH};downloadfilename=model;name=binary \
    https://raw.githubusercontent.com/EdgeFirstAI/model/v${PV}/LICENSE;downloadfilename=${PN}-LICENSE;name=license \
    https://maivin.deepviewml.com/models/people/modelpack-people-${MODEL_VERSION}.tflite;name=people \
    https://maivin.deepviewml.com/models/people/modelpack-people-mask-${MODEL_VERSION}.tflite;name=people-mask \
    https://maivin.deepviewml.com/models/people/modelpack-people-detect-${MODEL_VERSION}.tflite;name=people-detect \
    file://model.service \
    file://model.default \
"
SRC_URI[license.sha256sum] = "acbbda305958ff27afe43eeef4a77d48ef9d99364e772ba319d1d38ae759ae43"
SRC_URI[people.sha256sum] = "db168a433407a3c94cb10048ed82dbd45fee607f3eb5d05a449e58e91d17e285"
SRC_URI[people-mask.sha256sum] = "253e3a7608b0e844a800de1ed7ace6b5bec47a2fe51eb330763a40af9dc89180"
SRC_URI[people-detect.sha256sum] = "d3300690781a436470eea52c5753402718ef25e5c8ce095d0a3f876dbeffab4f"

BINARY_SHA256SUM[aarch64] = "3ef978684476b1cd94bea86a5bee50384f404e9942bbaddf704584bb1ffdda95"
BINARY_SHA256SUM[x86_64] = "fd0ff9dc47eb86c716ad3ff6bfd8f0f5939e4fece7c845f2c96b09061985e2ab"

python () {
    arch = d.getVar('TARGET_ARCH')
    sha256 = d.getVarFlag('BINARY_SHA256SUM', arch)
    if sha256:
        d.setVarFlag('SRC_URI', 'binary.sha256sum', sha256)
}

RDEPENDS:${PN} = "tflite"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/model.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/model.default ${D}${sysconfdir}/default/model

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/model ${D}${bindir}/model

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
