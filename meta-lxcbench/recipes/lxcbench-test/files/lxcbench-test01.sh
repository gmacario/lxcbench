#!/bin/sh

# ==================================================================================
# Project: 	LXCBENCH
#
# Description:	lxcbench-test01
# ==================================================================================

set -x

PTS_TARBALL=phoronix-test-suite-4.4.1.tar.gz
PTS_INSTALLDIR=~/phoronix-test-suite
PTS_WORKDIR=~/.phoronix-test-suite

PROGNAME=`basename $0`
echo "INFO: ${PROGNAME} v0.1"


if [ `whoami` != "root" ]; then
	echo "ERROR: Must run as root"
	exit 1
fi

# -----------------------------------------------------------------------------------
# Manually Install PTS from .tar.gz

## Manually install PTS dependencies
apt-get install php5-cli php5-curl php-fpdf

## Download and install PTS
mkdir -p ${PTS_INSTALLDIR}

cd ~/Downloads
wget http://www.phoronix.net/downloads/phoronix-test-suite/releases/${PTS_TARBALL}
tar xvfz ${PTS_TARBALL} -C ~

# -----------------------------------------------------------------------------------
# Launch PTS and accept User Agreement

mkdir -p ${PTS_INSTALLDIR}
cd ${PTS_INSTALLDIR}
./phoronix-test-suite <<EOT
Y
Y
Y
EOT

# -----------------------------------------------------------------------------------
# Perform LXCBENCH tests on Ubuntu

cd ${PTS_INSTALLDIR}

## Install dependencies for PTS tests
./phoronix-test-suite install-dependencies pts/cachebench pts/dbench pts/c-ray pts/encode-mp3 pts/stream

## Download and install the tests
./phoronix-test-suite install pts/cachebench pts/dbench pts/c-ray pts/encode-mp3 pts/stream

## Configure Batch Mode
./phoronix-test-suite batch-setup <<EOT
y
n
n
n
n
n
y
EOT

## Run Batch Mode
./phoronix-test-suite batch-run pts/cachebench pts/dbench pts/c-ray pts/encode-mp3 pts/stream

# Look at test esults
ls -la ${PTS_WORKDIR}/test-results/


# === EOF ===
