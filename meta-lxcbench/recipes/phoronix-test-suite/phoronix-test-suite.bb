DESCRIPTION = "Phoronix Test Suite"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"
RDEPENDS = "php-cli"
PV = "4.4.1"
PR = "r1"

SRC_URI = "\
    http://www.phoronix.net/downloads/phoronix-test-suite/releases/${PN}-${PV}.tar.gz \
"
SRC_URI[md5sum] = "54e98f06483bcb4f900f7813154b9f3a"
SRC_URI[sha256sum] = "96b4c928241e4847e00109f40b26c1c186e6284264fda747c96db1dbe2f3b3a3"

do_unpack() {
    echo "DEBUG: Custom do_unpack() start"
    tar xvfz ${DL_DIR}/${PN}-${PV}.tar.gz
    mv ${PN}/* ${PN}-${PV}/
    rmdir ${PN}
    #patch -p1 destdir.patch
    #patch -p1 makefile.patch
    echo "DEBUG: Custom do_unpack() end"
}

do_install() {
    echo "DEBUG: Custom do_install() start"
    DESTDIR=${D} ${S}/install-sh /usr
    #install -d ${D}${bindir}
    #install -m 0755 ${WORKDIR}/lxcbench-test01.sh ${D}${bindir}
    echo "DEBUG: Custom do_install() end"
}

# === EOF ===
