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
# Perform LXCBENCH tests
# -----------------------------------------------------------------------------------

cd ${PTS_INSTALLDIR}

## Install dependencies for PTS tests
${PTS_EXE} install-dependencies pts/cachebench pts/dbench pts/c-ray pts/encode-mp3 pts/stream

## Download and install the tests
${PTS_EXE} install pts/cachebench pts/dbench pts/c-ray pts/encode-mp3 pts/stream

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
${PTS_EXE} batch-run pts/cachebench pts/dbench pts/c-ray pts/encode-mp3 pts/stream

# -----------------------------------------------------------------------------------
# Look at test results
# -----------------------------------------------------------------------------------

ls -la ${PTS_USERDIR}/test-results/


# === EOF ===
