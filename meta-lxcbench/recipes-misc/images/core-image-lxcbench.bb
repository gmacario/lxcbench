DESCRIPTION = "An image suited for running the LXCBENCH test suite."

LICENSE = "GPLv2"

PV = "1.1"
PR = "r5"

require recipes-core/images/core-image-base.bb
#require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL_append = "\
    packagegroup-lxcbench-base \
    lxcbench-test \
    phoronix-test-suite \
    pts-dbench \
    pts-stream \
"

# Add 256MB extra space to image rootfs to store PTS test results
IMAGE_ROOTFS_EXTRA_SPACE ?= "524288"

# EOF
