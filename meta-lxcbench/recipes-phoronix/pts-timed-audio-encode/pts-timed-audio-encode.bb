DESCRIPTION = "pts/timed-audio-encode - Track 10 from the Nine Inch Nails \"The Slip\" CD"

HOMEPAGE = "http://dl.nin.com/theslip/signup"
LICENSE = "CC-BY-NC-SA-3.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/CC-BY-NC-SA-3.0;md5=b285975b5e439d99c95bcba5b5a8cf39"

#DEPENDS = "phoronix-test-suite"
PV = "1.0.0"
PR = "r2"

TEST_PN = "timed-audio-encode"

#ARCHIVE_PN = "lame"
#ARCHIVE_PV = "3.99.3"

PTS_USERDIR = "/home/root/.phoronix-test-suite"
PTS_PROFDIR = "${PTS_USERDIR}/test-profiles/pts/${TEST_PN}-${PV}"
PTS_TESTDIR = "${PTS_USERDIR}/installed-tests/pts/${TEST_PN}-${PV}"

TEST_PROFILE = "${TEST_PN}-${PV}.zip"

SRC_URI = "\
    file://${TEST_PROFILE} \
    http://www.phoronix-test-suite.com/benchmark-files/pts-trondheim-wav-3.tar.gz \
    file://pts-install.xml \
"

SRC_URI[md5sum] = "49b8f2f98ed71af16866e2df580e14c3"
SRC_URI[sha256sum] = "dc76edcaf74d17476e74656dee0785b07f97be30e89c53cf1be0a86981232a6d"

prefix = "${PTS_TESTDIR}"
#bindir = "${prefix}/lame_/bin"

#inherit autotools

##TARGET_CC_ARCH += "${LDFLAGS}"

FILES_${PN} += " ${PTS_PROFDIR}/*"
FILES_${PN} += " ${prefix}/pts-trondheim.wav"
FILES_${PN} += " ${prefix}/pts-install.xml"
#FILES_${PN} += " ${bindir}/lame*"
#FILES_${PN}-dbg += " ${bindir}/.debug/lame*"

#do_unpack() {
#    tar xvfz ${DL_DIR}/${ARCHIVE_PN}-${ARCHIVE_PV}.tar.gz
#    mkdir -p ${PN}-${PV}
#    mv ${ARCHIVE_PN}-${ARCHIVE_PV}/* ${PN}-${PV}/
#    rm -rf ${ARCHIVE_PN}-${ARCHIVE_PV}
#}

#do_unpack_append2() {
#    echo "DEBUG: do_unpack_append2() start"
#    echo "DEBUG: do_unpack_append2() end"
#}

#do_unpack_append() {
#    bb.build.exec_func('do_unpack_append2', d)
#}

#do_configure() {
#    ./configure --host=${HOST_SYS}
#}

#do_compile() {
#    echo "DEBUG: do_compile() start"
#    echo "DEBUG: do_compile() end"
#}

do_install() {
    install -d ${D}${PTS_PROFDIR}
    unzip ${FILESDIR}/${TEST_PROFILE} -d ${D}${PTS_PROFDIR}

    install -d ${D}${prefix}
    install -m 0644 ${WORKDIR}/pts-trondheim-3.wav ${D}${prefix}/pts-trondheim.wav
    install -m 0644 ${FILESDIR}/pts-install.xml ${D}${prefix}
}

#do_install_append() {
#    echo "DEBUG: Custom do_install_append() start"
#
#    install -d ${D}${PTS_PROFDIR}
#
#    install -d ${D}${prefix}
#    install -d ${D}${bindir}
#    install -m 0755 frontend/lame ${D}${bindir}
#    cat << END >${D}${prefix}/lame
##!/bin/sh                                                                                                                                         
#./lame_/bin/lame -h \$TEST_EXTENDS/pts-trondheim.wav >/dev/null 2>&1
#echo \$? > ~/test-exit-status"
#END
#    chmod +x ${D}${prefix}/lame
#
#    unzip ${FILESDIR}/${TEST_PROFILE} -d ${D}${PTS_PROFDIR}
#    install -m 0644 ${FILESDIR}/pts-install.xml ${D}${prefix}
#
#    echo "DEBUG: Custom do_install_append() end"
#}

# === EOF ===
