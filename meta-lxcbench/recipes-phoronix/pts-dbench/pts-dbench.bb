# WORK IN PROGRESS!!!
# Adapted from http://cgit.openembedded.org/cgit.cgi/meta-openembedded/tree/meta-oe/recipes-benchmark/dbench/dbench_4.0.bb

DESCRIPTION = "PTS - dbench Disk Benchmark"
HOMEPAGE = "http://samba.org/ftp/tridge/dbench/"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

DEPENDS = "phoronix-test-suite popt"
PV = "1.0.0"
PR = "r2"

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
"

SRC_URI[md5sum] = "1fe56ff71b9a416f8889d7150ac54da4"
SRC_URI[sha256sum] = "6001893f34e68a3cfeb5d424e1f2bfef005df96a22d86f35dc770c5bccf3aa8a"

prefix = "${PTS_TESTDIR}"
bindir = "${prefix}/dbench_"

EXTRA_OECONF := "--prefix=${PTS_TESTDIR}"

inherit autotools

TARGET_CC_ARCH += "${LDFLAGS}"

FILES_${PN} += " ${PTS_PROFDIR}/*"
FILES_${PN} += " ${prefix}/client.txt"
FILES_${PN} += " ${bindir}/dbench*"
FILES_${PN} += " ${bindir}/tbench*"
FILES_${PN}-dbg += " ${bindir}/.debug/dbench*"
FILES_${PN}-dbg += " ${bindir}/.debug/tbench*"

do_unpack() {
    echo "DEBUG: Custom do_unpack() start"
    tar xvfz ${DL_DIR}/${SRC_PN}-${SRC_PV}.tar.gz
    mv ${SRC_PN}-${SRC_PV}/* ${PN}-${PV}/
    rmdir ${SRC_PN}-${SRC_PV}
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

    echo "DEBUG: Custom do_install_append() end"
}

# From http://git.projects.genivi.org/?p=lxcbench.git;a=blob;f=contrib/thesis-fcastagnotto/script-dbench.sh
# ######################################################################################
# #   DBENCH-1.0.0								     #
# ######################################################################################
# #This script run the download of the source of the Phoronix test Dbench-1.0.0
# #and the cross compilation to functioning on a ARM linux architecture.
# #It require the test files (that can be downloaded on the target by the Phoronix Suit
# #by the command "phoronix-test-suite download-test-files pts/dbench") in the
# #same folder of this script.
# 
# #!/bin/bash
# 
# unzip dbench-1.0.0.zip
# wget $(xpath -e "//URL" downloads.xml 2>/dev/null |  sed -e 's/<\/*URL>//g' -e 's/<URL>//g' -e 's/<\/URL>/ /g')
# sudo ./install-dbench.sh
# ./script-pts-xml.sh dbench-1.0.0

# From http://git.projects.genivi.org/?p=lxcbench.git;a=blob;f=contrib/thesis-fcastagnotto/install-dbench.sh
# ######################################################################################
# #   INSTALL DBENCH-1.0.0                                                             #
# ######################################################################################
# #This script run the cross-compilation of the Dbench test.
# 
# #!/bin/sh
# 
# tar -zxvf dbench-4.0.tar.gz
# mkdir $HOME/dbench_/
# cd dbench-4.0/
# 
# ./autogen.sh
# ./configure -build=i686-linux --host=arm-linux-gnueabi --prefix=$HOME/dbench_/
# 
# make -j $NUM_CPU_JOBS
# echo $? > ~/install-exit-status
# make install
# cp client.txt ../
# cd ..
# rm -rf dbench-4.0/
# 
# echo "#!/bin/sh
# ./dbench_/bin/dbench \$@ -c client.txt > \$LOG_FILE 2>&1
# echo \$? > ~/test-exit-status" > dbench
# 
# chmod +x dbench

# From http://git.projects.genivi.org/?p=lxcbench.git;a=blob;f=contrib/thesis-fcastagnotto/script-pts-xml.sh
# ######################################################################################
# #   scripts-pts-xml.sh
# ######################################################################################
# #This script generates the pts-install.xml file, needed by the Phoronix Suite to correctly run the test.
# #It assumes to default that the user on the target system is "debian". If not, change it in the href field.
# 
# #!/bin/bash
# 
# if [ -z $1 ];
# then
# 	echo "ERROR: missing parameter (name of test)"
# else
# 
# echo "<?xml version="1.0"?>
# <!--Phoronix Test Suite v4.4.1 (Forsand)-->
# <?xml-stylesheet type="text/xsl" href="file:////home/debian/.phoronix-test-suite/xsl/pts-test-installation-viewer.xsl"?>
# <PhoronixTestSuite>
#   <TestInstallation>
#     <Environment>
#       <Identifier>pts/$1</Identifier>
#       <Version>1.0.0</Version>
#       <CheckSum>dc383f29025b00a6cad1f825d2b87b99</CheckSum>
#       <CompilerData>{"compiler-type":"CC","compiler":"gcc","compiler-options":"-lpopt"}</CompilerData>
#       <SystemIdentifier>QVJNdjcgcmV2IDVfX0ZyZWVzY2FsZSBpLk1YNTMgKERldmljZSBUcmVlIFN1cHBvcnQpX19EZWJpYW4gNi4wLjdfX0dDQyA0LjQuNQ==</SystemIdentifier>
#     </Environment>
#     <History>
#       <InstallTime>2013-05-03 16:11:35</InstallTime>
#       <InstallTimeLength>139</InstallTimeLength>
#       <LastRunTime>0000-00-00 00:00:00</LastRunTime>
#       <TimesRun>0</TimesRun>
#       <AverageRunTime></AverageRunTime>
#       <LatestRunTime></LatestRunTime>
#     </History>
#   </TestInstallation>
# </PhoronixTestSuite>
# 
# " > pts-install.xml
# chmod 0777 pts-install.xml
# 
# fi

# === EOF ===
