DESCRIPTION = "Maivin Model Service"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e153ccee5db0d7cbd514bc6ba454f981"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://maivin.deepviewml.com/services/model/maivin-model-${PV};name=model \
    https://github.com/DeepViewML/peopledetect/releases/download/1.0/peopledetect.rtm;name=peopledetect \
    https://github.com/DeepViewML/peoplesegment/releases/download/1.0/peoplesegment.rtm;name=peoplesegment \
    https://github.com/DeepViewML/coco/releases/download/v0.1/peoplesegdet.rtm;name=peoplesegdet \
    https://github.com/DeepViewML/facedetect/releases/download/1.0/facedetect.rtm;name=facedetect \
    file://model.service \
    file://model.default \
    file://LICENSE \
"
SRC_URI[model.sha256sum] = "4312f5d7bbe598fa8a245deac397bfe5581369fe2a93fe7330c3ef0ca6e78663"
SRC_URI[peopledetect.sha256sum] = "d80c410d54eb33a83df8ac7bfd5d3bca5ba321bb5ac02c318d3817b6d5726b3d"
SRC_URI[peoplesegment.sha256sum] = "2f37bfd00e9b14fd6a5379db87c6f4a0c413b30fa0b3c8da78657a2b2089fc1e"
SRC_URI[peoplesegdet.sha256sum] = "d733037c22a81c35d0e7f7febbbc10c115a0aaf13d0e355e0e24aef3cc5f71b8"
SRC_URI[facedetect.sha256sum] = "374b081671c42f2d4b73ed6fe71e46bfaa73ec122a6b0c532310afd367a53a82"

DEPENDS = "vaal deepview-rt"
RDEPENDS_${PN} = "libvaal libdeepview-rt"

S = "${WORKDIR}"

inherit features_check systemd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/model.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/model.default ${D}${sysconfdir}/default/model

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/maivin-model-${PV} ${D}${bindir}/model

    install -d ${D}${datadir}/model
    install -m 0644 ${WORKDIR}/peopledetect.rtm ${D}${datadir}/model
    install -m 0644 ${WORKDIR}/peoplesegment.rtm ${D}${datadir}/model
    install -m 0644 ${WORKDIR}/facedetect.rtm ${D}${datadir}/model
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "model.service"
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${systemd_system_unitdir}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${datadir}"
