# TODO: Adapted from meibp-2013/core-image-atp.bb

DESCRIPTION = "A console-only image that fully supports the target device \
hardware."

LICENSE = "GPLv2"

#require recipes-core/images/core-image-base.bb
require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL_append = "\
	packagegroup-lxcbench-base \
	lxcbench-test \
"


# EOF
