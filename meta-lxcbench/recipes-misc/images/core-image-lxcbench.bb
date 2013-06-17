DESCRIPTION = "An image suited for running the LXCBENCH test suite."

LICENSE = "GPLv2"

PV = "1.1"
PR = "r6"

require recipes-core/images/core-image-base.bb
#require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL_append = "\
    packagegroup-lxcbench-base \
    lxcbench-test \
    phoronix-test-suite \
    pts-c-ray \
    pts-cachebench \
    pts-dbench \
    pts-encode-mp3 \
    pts-stream \
"

# Required by lxc-create -t busybox
IMAGE_INSTALL_append += " bash"
IMAGE_INSTALL_append += " coreutils"

# Make sure to have at least 256MB free space
# inside image rootfs for storing PTS test results.
#
# References:
# - poky/meta/classes/image_types.bbclass
# - meta-fsl-arm/classes/image_types_fsl.bbclass
#
IMAGE_ROOTFS_SIZE ?= "300000"

# EOF
