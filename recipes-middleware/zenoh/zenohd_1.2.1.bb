DESCRIPTION = "Zero Overhead Network Protocol"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    https://github.com/eclipse-zenoh/zenoh/releases/download/${PV}/zenoh-${PV}-${TARGET_ARCH}-unknown-linux-gnu-standalone.zip \
    file://zenohd.service \
    file://zenohd.default \
    file://zenohd.yaml \
"

SRC_URI[sha256sum] = "df9ed7fa34e16c6c602f95126b15542c33641e6363786de595d156fea7de9a93"

inherit features_check systemd

do_install[depends] += "unzip-native:do_populate_sysroot"

do_install () {
    install -d ${D}${bindir}
    install -d ${D}${libdir}
    install -d ${D}${sysconfdir}/default
    install -d ${D}${systemd_system_unitdir}

    install -m 0644 ${WORKDIR}/zenohd.default ${D}${sysconfdir}/default/zenohd
    install -m 0644 ${WORKDIR}/zenohd.yaml ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/zenohd.service ${D}${systemd_system_unitdir}

    ${bindir}/env unzip -q -o ${DL_DIR}/zenoh-${PV}-${TARGET_ARCH}-unknown-linux-gnu-standalone.zip -d ${D}${libdir}
    mv ${D}${libdir}/zenohd ${D}${bindir}
}

REQUIRED_DISTRO_FEATURES = "systemd"
SYSTEMD_SERVICE:${PN} = "zenohd.service"
SYSTEMD_AUTO_ENABLE = "disable"

FILES:${PN}-dev = ""
FILES:${PN} += "${bindir}/zenohd"
FILES:${PN} += "${libdir}/libzenoh_plugin_storage_manager.so"
FILES:${PN} += "${libdir}/libzenoh_plugin_rest.so"
FILES:${PN} += "${sysconfdir}/default/zenohd"
FILES:${PN} += "${sysconfdir}/zenohd.yaml"
FILES:${PN} += "${systemd_system_unitdir}"
