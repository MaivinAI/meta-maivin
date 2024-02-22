FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://segmentation.service"
SRC_URI += "file://detection.service"
SRC_URI += "file://facedetect.service"
SRC_URI += "file://faceblur.service"
SRC_URI += "file://headpose.service"
SRC_URI += "file://bodypose.service"