DESCRIPTION = "PTS - dbench Disk Benchmark"
HOMEPAGE = "http://samba.org/ftp/tridge/dbench/"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

DEPENDS = "phoronix-test-suite popt"
PV = "1.0.0"
PR = "r3"

SRC_PN = "dbench"
SRC_PV = "4.0"

PTS_USERDIR = "/home/root/.phoronix-test-suite"
PTS_PROFDIR = "${PTS_USERDIR}/test-profiles/pts/${SRC_PN}-${PV}"
PTS_TESTDIR = "${PTS_USERDIR}/installed-tests/pts/${SRC_PN}-${PV}"

TEST_PROFILE = "${SRC_PN}-${PV}.zip"

SRC_URI = "\
    file://${TEST_PROFILE} \
    http://samba.org/ftp/tridge/dbench/${SRC_PN}-${SRC_PV}.tar.gz \
    file://destdir.patch \
    file://makefile.patch \
    file://launch-dbench.sh \
    file://pts-install.xml \
"

SRC_URI[md5sum] = "1fe56ff71b9a416f8889d7150ac54da4"
SRC_URI[sha256sum] = "6001893f34e68a3cfeb5d424e1f2bfef005df96a22d86f35dc770c5bccf3aa8a"

prefix = "${PTS_TESTDIR}"
bindir = "${prefix}/dbench_/bin"

EXTRA_OECONF := "--prefix=${PTS_TESTDIR}"

inherit autotools

TARGET_CC_ARCH += "${LDFLAGS}"

FILES_${PN} += " ${PTS_PROFDIR}/*"
FILES_${PN} += " ${bindir}/dbench*"
FILES_${PN} += " ${bindir}/tbench*"
FILES_${PN} += " ${prefix}/client.txt"
FILES_${PN} += " ${prefix}/dbench"
FILES_${PN} += " ${prefix}/pts-install.xml"
FILES_${PN}-dbg += " ${bindir}/.debug/dbench*"
FILES_${PN}-dbg += " ${bindir}/.debug/tbench*"

do_unpack() {
    echo "DEBUG: Custom do_unpack() start"

    tar xvfz ${DL_DIR}/${SRC_PN}-${SRC_PV}.tar.gz
    mv ${SRC_PN}-${SRC_PV} ${PN}-${PV}
    patch -p1 destdir.patch
    patch -p1 makefile.patch

    echo "DEBUG: Custom do_unpack() end"
}

do_install_append() {
    echo "DEBUG: Custom do_install_append() start"

    install -d ${D}${PTS_PROFDIR}
    unzip ${FILESDIR}/${TEST_PROFILE} -d ${D}${PTS_PROFDIR}

    install -d ${D}${prefix}
    install -m 0644 ${S}/client.txt ${D}${prefix}
    rm ${D}${prefix}/share/client.txt

    install -m 0755 ${FILESDIR}/launch-dbench.sh ${D}${prefix}/dbench
    install -m 0644 ${FILESDIR}/pts-install.xml ${D}${prefix}

    echo "DEBUG: Custom do_install_append() end"
}

# === EOF ===
