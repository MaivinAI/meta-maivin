do_install() {
	install -d ${D}${libdir}/firmware/hailo
	install -m 0755 ${FW_PATH} ${D}${libdir}/firmware/hailo/hailo8_fw.bin
}

FILES:${PN} = "${libdir}/firmware/hailo/hailo8_fw*"
