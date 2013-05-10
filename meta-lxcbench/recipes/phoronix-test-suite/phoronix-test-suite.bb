DESCRIPTION = "Phoronix Test Suite"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"
RDEPENDS = "php-cli"
PV = "1.1"
PR = "r1"

#SRC_URI = "\
#	file://lxcbench-test01.sh \
#"

do_install() {
	install -d ${D}${bindir}
	#install -m 0755 ${WORKDIR}/lxcbench-test01.sh ${D}${bindir}
	echo "No files installed on target because of GPLv3"
}
