DESCRIPTION = "pts/c-ray - C-Ray test (Processor)"

#HOMEPAGE = "http://lame.sourceforge.net/"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "phoronix-test-suite"
PV = "1.1.0"
PR = "r3"

TEST_PN = "c-ray"

ARCHIVE_PN = "c-ray"
ARCHIVE_PV = "1.1"

PTS_USERDIR = "/home/root/.phoronix-test-suite"
PTS_PROFDIR = "${PTS_USERDIR}/test-profiles/pts/${TEST_PN}-${PV}"
PTS_TESTDIR = "${PTS_USERDIR}/installed-tests/pts/${TEST_PN}-${PV}"

TEST_PROFILE = "${TEST_PN}-${PV}.zip"

SRC_URI = "\
    file://${TEST_PROFILE} \
    http://www.phoronix-test-suite.com/benchmark-files/c-ray-1.1.tar.gz \
    file://pts-install.xml \
"

SRC_URI[md5sum] = "5b0b939c953dc7f7eb03fae2c1526d33"
SRC_URI[sha256sum] = "6f507aae47a9367334b8cb50f50eb4ad0f6fef99aeae9f2f7d55ba9818e798bf"

prefix = "${PTS_TESTDIR}"
bindir = "${prefix}/c-ray-1.1"

inherit autotools

#TARGET_CC_ARCH += "${LDFLAGS}"

FILES_${PN} += " ${PTS_PROFDIR}/*"
FILES_${PN} += " ${prefix}/c-ray"
FILES_${PN} += " ${prefix}/pts-install.xml"
FILES_${PN} += " ${bindir}/c-ray-mt*"
FILES_${PN} += " ${bindir}/scene"
FILES_${PN} += " ${bindir}/sphfract"
FILES_${PN}-dbg += " ${bindir}/.debug/c-ray-mt*"

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
    rm -f Makefile.OLD
    mv Makefile Makefile.OLD
    sed 's/^CC.*$//g' Makefile.OLD >Makefile
}

#do_compile() {
#    echo "DEBUG: do_compile() start"
#    echo "DEBUG: do_compile() end"
#}

do_install() {
    echo "DEBUG: do_install() start"

    install -d ${D}${PTS_PROFDIR}

    install -d ${D}${prefix}
    install -d ${D}${bindir}
    install -m 0755 c-ray-mt ${D}${bindir}
    install -m 0644 scene ${D}${bindir}
    install -m 0644 sphfract ${D}${bindir}
    cat << END >${D}${prefix}/c-ray
#!/bin/sh
cd c-ray-1.1/
RT_THREADS=\$((\$NUM_CPU_CORES * 16))
./c-ray-mt -t \$RT_THREADS -s 1600x1200 -r 8 -i sphfract -o output.ppm > \$LOG_FILE 2>&1
echo \$? > ~/test-exit-status
END
    chmod +x ${D}${prefix}/c-ray

    unzip ${FILESDIR}/${TEST_PROFILE} -d ${D}${PTS_PROFDIR}
    install -m 0644 ${FILESDIR}/pts-install.xml ${D}${prefix}

    echo "DEBUG: do_install() end"
}

#do_install_append() {
#    echo "DEBUG: do_install_append() start"
#    echo "DEBUG: do_install_append() end"
#}

# === EOF ===
