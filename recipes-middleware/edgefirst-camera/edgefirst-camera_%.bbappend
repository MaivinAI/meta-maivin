FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://camera.service \
"

RDEPENDS:${PN}:append = " imx8-isp imx-vpu-hantro-vc"

SYSTEMD_SERVICE:${PN} = "camera.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    # Replace edgefirst-prefixed service with Maivin-named service
    rm -f ${D}${systemd_system_unitdir}/edgefirst-camera.service
    if [ "${UNPACKDIR}" != "" ]; then
        install -m 0644 ${UNPACKDIR}/camera.service ${D}${systemd_system_unitdir}/camera.service
    else
        install -m 0644 ${WORKDIR}/camera.service ${D}${systemd_system_unitdir}/camera.service
    fi

    # Add short-name symlink for binary
    ln -sf edgefirst-camera ${D}${bindir}/camera
}
