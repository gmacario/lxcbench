DESCRIPTION = "pts/encode-mp3 - LAME MP3 Encoding"

HOMEPAGE = "http://lame.sourceforge.net/"
LICENSE = "LGPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/LGPL-2.0;md5=9427b8ccf5cf3df47c29110424c9641a"

DEPENDS = "phoronix-test-suite"
PV = "1.4.0"
PR = "r0"

TEST_PN = "encode-mp3"

ARCHIVE_PN = "lame"
ARCHIVE_PV = "3.99.3"

PTS_USERDIR = "/home/root/.phoronix-test-suite"
PTS_PROFDIR = "${PTS_USERDIR}/test-profiles/pts/${TEST_PN}-${PV}"
PTS_TESTDIR = "${PTS_USERDIR}/installed-tests/pts/${TEST_PN}-${PV}"

TEST_PROFILE = "${TEST_PN}-${PV}.zip"

SRC_URI = "\
    file://${TEST_PROFILE} \
    http://superb-sea2.dl.sourceforge.net/sourceforge/lame/${ARCHIVE_PN}-${ARCHIVE_PV}.tar.gz \
    file://pts-install.xml \
"

SRC_URI[md5sum] = "5ad31e33e70455eb3a7b79a5dd934fce"
SRC_URI[sha256sum] = "d4ea3c8d00d2cc09338425a25dbfeb4d587942cb3c83a677c09aeb1e850c74cf"

prefix = "${PTS_TESTDIR}"
bindir = "${prefix}/lame_/bin"

inherit autotools

#TARGET_CC_ARCH += "${LDFLAGS}"

FILES_${PN} += " ${PTS_PROFDIR}/*"
FILES_${PN} += " ${prefix}/lame"
FILES_${PN} += " ${prefix}/pts-install.xml"
FILES_${PN} += " ${bindir}/lame*"
FILES_${PN}-dbg += " ${bindir}/.debug/lame*"

do_unpack() {
    tar xvfz ${DL_DIR}/${ARCHIVE_PN}-${ARCHIVE_PV}.tar.gz
    mkdir -p ${PN}-${PV}
    mv ${ARCHIVE_PN}-${ARCHIVE_PV}/* ${PN}-${PV}/
    rm -rf ${ARCHIVE_PN}-${ARCHIVE_PV}
}

#do_unpack_append2() {
#    echo "DEBUG: do_unpack_append2() start"
#    echo "DEBUG: do_unpack_append2() end"
#}

#do_unpack_append() {
#    bb.build.exec_func('do_unpack_append2', d)
#}

do_configure() {
    ./configure --host=${HOST_SYS}
}

#do_compile() {
#    echo "DEBUG: do_compile() start"
#    echo "DEBUG: do_compile() end"
#}

do_install_append() {
    echo "DEBUG: Custom do_install_append() start"

    install -d ${D}${PTS_PROFDIR}

    install -d ${D}${prefix}
    install -d ${D}${bindir}
    install -m 0755 frontend/lame ${D}${bindir}
    cat << END >${D}${prefix}/lame
#!/bin/sh                                                                                                                                         
./lame_/bin/lame -h \$TEST_EXTENDS/pts-trondheim.wav >/dev/null 2>&1
echo \$? > ~/test-exit-status"
END
    chmod +x ${D}${prefix}/lame

    unzip ${FILESDIR}/${TEST_PROFILE} -d ${D}${PTS_PROFDIR}
    install -m 0644 ${FILESDIR}/pts-install.xml ${D}${prefix}

    echo "DEBUG: Custom do_install_append() end"
}

# === EOF ===
