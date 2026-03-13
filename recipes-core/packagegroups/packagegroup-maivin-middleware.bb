SUMMARY = "Maivin middleware services"
DESCRIPTION = "EdgeFirst middleware services for the Maivin AI vision platform"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
    maivin \
    edgefirst-camera \
    edgefirst-imu \
    edgefirst-navsat \
    edgefirst-radarpub \
    edgefirst-lidarpub \
    edgefirst-model \
    edgefirst-fusion \
    edgefirst-websrv \
    edgefirst-webui \
    edgefirst-recorder \
    edgefirst-replay \
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
