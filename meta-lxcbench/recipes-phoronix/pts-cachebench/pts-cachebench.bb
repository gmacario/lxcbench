# TODO TODO TODO

DESCRIPTION = "pts/cachebench - CacheBench test (Processor)"

#HOMEPAGE = "http://samba.org/ftp/tridge/dbench/"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

DEPENDS = "phoronix-test-suite"
PV = "1.0.0"
PR = "r0"

SRC_PN = "cachebench"
#SRC_PV = "2009-04-11"

PTS_USERDIR = "/home/root/.phoronix-test-suite"
PTS_PROFDIR = "${PTS_USERDIR}/test-profiles/pts/${SRC_PN}-${PV}"
PTS_TESTDIR = "${PTS_USERDIR}/installed-tests/pts/${SRC_PN}-${PV}"

TEST_PROFILE = "${SRC_PN}-${PV}.zip"

SRC_URI = "\
    file://${TEST_PROFILE} \
    http://icl.cs.utk.edu/projects/llcbench/llcbench.tar.gz \
    file://pts-install.xml \
"

SRC_URI[md5sum] = "2334a4b679e4f63c5c646cc8d9ff3f25"
SRC_URI[sha256sum] = "4f01a7acd162105ed85bb953c33edffdf7c9333b85672596a393dedfd0e8b070"

prefix = "${PTS_TESTDIR}"
bindir = "${prefix}/llcbench/cachebench"

#EXTRA_OECONF := "--prefix=${PTS_TESTDIR}"

##inherit autotools

##TARGET_CC_ARCH += "${LDFLAGS}"

FILES_${PN} += " ${PTS_PROFDIR}/*"
FILES_${PN} += " ${prefix}/cachebench"
FILES_${PN} += " ${prefix}/pts-install.xml"
FILES_${PN} += " ${bindir}/cachebench*"
FILES_${PN}-dbg += " ${bindir}/.debug/cachebench*"

do_unpack() {
    tar xvfz ${DL_DIR}/llcbench.tar.gz
    mkdir -p ${PN}-${PV}
    mv llcbench/* ${PN}-${PV}/
    rm -rf llcbench
}

#do_unpack_append2() {
#    echo "DEBUG: do_unpack_append2() start"
#    mv ${WORKDIR}/stream.c ${S}
#    echo "DEBUG: do_unpack_append2() end"
#}

#do_unpack_append() {
#    bb.build.exec_func('do_unpack_append2', d)
#}

do_configure() {
    ${MAKE} linux-mpich
    rm -f sys.def.OLD
    mv sys.def sys.def.OLD
    sed 's/gcc/\$\{CC\}/g' sys.def.OLD >sys.def
}

do_compile() {
    ${MAKE} cache-bench
}

do_install_append() {
    echo "DEBUG: Custom do_install_append() start"

    install -d ${D}${PTS_PROFDIR}

    install -d ${D}${prefix}
    install -d ${D}${prefix}/llcbench
    install -d ${D}${prefix}/llcbench/cachebench
    install -m 0755 cachebench/cachebench ${D}${bindir}
    cat << END >${D}${prefix}/cachebench
#!/bin/sh                                                                                                                                         
cd llcbench/cachebench/
./cachebench > \$LOG_FILE 2>&1                                                                                                                          
echo \$? > ~/test-exit-status"
END
    chmod +x ${D}${prefix}/cachebench

    unzip ${FILESDIR}/${TEST_PROFILE} -d ${D}${PTS_PROFDIR}
    install -m 0644 ${FILESDIR}/pts-install.xml ${D}${prefix}

    echo "DEBUG: Custom do_install_append() end"
}

# === EOF ===
