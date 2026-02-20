SUMMARY = "Maivin middleware services"
DESCRIPTION = "EdgeFirst middleware services for the Maivin AI vision platform"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
    maivin \
    raivin \
    camera \
    imu \
    navsat \
    radarpub \
    lidarpub \
    model \
    fusion \
    websrv \
    webui \
    recorder \
    replay \
    publisher \
    localtime \
    edgefirst-client \
    edgefirst-client-python \
    edgefirst-schemas \
    edgefirst-schemas-python \
    mcap \
    zenohd \
    python3-zenoh \
"
