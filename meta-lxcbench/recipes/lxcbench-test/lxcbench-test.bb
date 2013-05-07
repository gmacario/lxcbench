DESCRIPTION = "LXCBENCH Test Scripts"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS = "php-cli"
PR = "r1"

SRC_URI = "\
	file://lxcbench-test01.sh \
"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/lxcbench-test01.sh ${D}${bindir}
}
