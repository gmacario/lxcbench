DESCRIPTION = "An image suited for running the LXCBENCH test suite."

LICENSE = "GPLv2"

PV = "1.1"
PR = "r3"

#require recipes-core/images/core-image-base.bb
require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL_append = "\
    packagegroup-lxcbench-base \
    lxcbench-test \
    phoronix-test-suite \
    pts-dbench \
"

# Add extra space to image rootfs to store PTS test results
IMAGE_ROOTFS_EXTRA_SPACE ?= "102400"

# EOF
