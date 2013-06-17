DESCRIPTION = "pts/stream - This benchmark tests the system memory (RAM) performance"

LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

DEPENDS = "phoronix-test-suite"
PV = "1.1.0"
PR = "r0"

SRC_PN = "stream"
SRC_PV = "2009-04-11"

PTS_USERDIR = "/home/root/.phoronix-test-suite"
PTS_PROFDIR = "${PTS_USERDIR}/test-profiles/pts/${SRC_PN}-${PV}"
PTS_TESTDIR = "${PTS_USERDIR}/installed-tests/pts/${SRC_PN}-${PV}"

TEST_PROFILE = "${SRC_PN}-${PV}.zip"

SRC_URI = "\
    file://${TEST_PROFILE} \
    http://www.phoronix-test-suite.com/benchmark-files/stream-2009-04-11.tar.gz \
    file://pts-install.xml \
"

SRC_URI[md5sum] = "db10871405a08a3873306fcb2688f729"
SRC_URI[sha256sum] = "4232fa4ff0f6418722679fe5468e5d302698529da49c829f9962867b02ebff90"

prefix = "${PTS_TESTDIR}"

EXTRA_OECONF := "--prefix=${PTS_TESTDIR}"

FILES_${PN} += " ${PTS_PROFDIR}/*"
FILES_${PN} += " ${prefix}/stream"
FILES_${PN} += " ${prefix}/stream-bin"
FILES_${PN} += " ${prefix}/pts-install.xml"
FILES_${PN}-dbg += " ${prefix}/.debug/stream-bin*"

do_unpack_append2() {
    echo "DEBUG: do_unpack_append2() start"
    mv ${WORKDIR}/stream.c ${S}
    echo "DEBUG: do_unpack_append2() end"
}

do_unpack_append() {
    bb.build.exec_func('do_unpack_append2', d)
}

do_compile() {
    #${CC} stream.c -O2 -fopenmp -o stream-bin
    ${CC} stream.c -O2 -o stream-bin
}

do_install_append() {
    echo "DEBUG: Custom do_install_append() start"

    install -d ${D}${PTS_PROFDIR}

    install -d ${D}${prefix}
    install -m 0755 stream-bin ${D}${prefix}
    cat << END >${D}${prefix}/stream                                                                                                                 
#!/bin/sh                                                                                                                                         
export OMP_NUM_THREADS=\$NUM_CPU_CORES                                                                                                                  
./stream-bin > \$LOG_FILE 2>&1                                                                                                                          
echo \$? > ~/test-exit-status"
END
    chmod +x ${D}${prefix}/stream

    unzip ${FILESDIR}/${TEST_PROFILE} -d ${D}${PTS_PROFDIR}
    install -m 0644 ${FILESDIR}/pts-install.xml ${D}${prefix}

    echo "DEBUG: Custom do_install_append() end"
}

# === EOF ===
