#!/bin/sh

# ==================================================================================
# Project: 	LXCBENCH
#
# Description:	LXCBENCH Test Suite #1
#
# See http://projects.genivi.org/lxcbench
# ==================================================================================

#set -x
set -e

PTS_TARBALL=phoronix-test-suite-4.4.1.tar.gz
PTS_INSTALLDIR=~/phoronix-test-suite
PTS_USERDIR=~/.phoronix-test-suite

PTS_NEEDED_TESTS=""
PTS_NEEDED_TESTS="${PTS_NEEDED_TESTS} pts/cachebench"
PTS_NEEDED_TESTS="${PTS_NEEDED_TESTS} pts/dbench"
PTS_NEEDED_TESTS="${PTS_NEEDED_TESTS} pts/c-ray"
PTS_NEEDED_TESTS="${PTS_NEEDED_TESTS} pts/encode-mp3"
PTS_NEEDED_TESTS="${PTS_NEEDED_TESTS} pts/stream"

PROGNAME=`basename $0`
echo "INFO: ${PROGNAME}"


if [ `whoami` != "root" ]; then
	echo "ERROR: Must run as root"
	exit 1
fi

# -----------------------------------------------------------------------------------
# Make sure date is properly set
# -----------------------------------------------------------------------------------

NTPD_EXE=`which ntpd`
if [ "${NTPD_EXE}" != "" ]; then
    echo "DEBUG: Setting up date via NTP"
    ${NTPD_EXE} -q -p ntp1.ien.it
fi

# -----------------------------------------------------------------------------------
# Manually Install PTS from .tar.gz
# -----------------------------------------------------------------------------------

PTS_EXE=`which phoronix-test-suite`
if [ "${PTS_EXE}" != "" ]; then
    PTS_INSTALLDIR="`dirname ${PTS_EXE}`"
    PTS_EXE="`basename ${PTS_EXE}`"
    echo "INFO: Using system-installed ${PTS_EXE}"
fi

if [ ! -x "${PTS_INSTALLDIR}/phoronix-test-suite" ]; then
    echo "INFO: Installing ${PTS_TARBALL} into ${PTS_INSTALLDIR}"

    # Manually install PTS dependencies
    which apt-get && apt-get install php5-cli php5-curl php-fpdf

    ## Download and install PTS
    mkdir -p ${PTS_INSTALLDIR}

    wget -O - \
	http://www.phoronix.net/downloads/phoronix-test-suite/releases/${PTS_TARBALL} \
	| tar xvz -C ${PTS_INSTALLDIR}/..

    PTS_EXE="./phoronix-test-suite"
fi

# -----------------------------------------------------------------------------------
# Launch PTS and accept User Agreement
# -----------------------------------------------------------------------------------

mkdir -p ${PTS_INSTALLDIR}
cd ${PTS_INSTALLDIR}
${PTS_EXE} <<EOT
Y
Y
Y
EOT

# -----------------------------------------------------------------------------------
# Make sure that the tests needed by LXCBENCH have been installed
# -----------------------------------------------------------------------------------

cd ${PTS_INSTALLDIR}
${PTS_EXE} list-installed-tests >/tmp/pts-installed-tests.txt

available_tests=""
for t in ${PTS_NEEDED_TESTS}; do
    if grep "${t}" /tmp/pts-installed-tests.txt >/dev/null; then
        echo "INFO: Test ${t} is already installed"
        available_tests="${available_tests} ${t}"
    else
        echo "INFO: Trying to install test ${t}"
        ${PTS_EXE} install-dependencies ${t} && \
        ${PTS_EXE} install ${t} && \
        available_tests="${available_tests} ${t}"
    fi
done
#echo "DEBUG: available_tests=${available_tests}"

# -----------------------------------------------------------------------------------
# Perform LXCBENCH tests
# -----------------------------------------------------------------------------------

## Configure Batch Mode
${PTS_EXE} batch-setup <<EOT
y
n
n
n
n
n
y
EOT

## Run Batch Mode
${PTS_EXE} batch-run ${PTS_NEEDED_TESTS}

# -----------------------------------------------------------------------------------
# Look at test results
# -----------------------------------------------------------------------------------

ls -la ${PTS_USERDIR}/test-results/


# === EOF ===
