#!/bin/sh

# ==================================================================================
# Project: 	LXCBENCH
#
# Description:	lxcbench-test01
# ==================================================================================

PROGNAME=`basename $0`
echo "INFO: ${PROGNAME} v0.1"

if [ `whoami` != "root" ]; then
	echo "ERROR: Must run as root"
	exit 1
fi

# === EOF ===
