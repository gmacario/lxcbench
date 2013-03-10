PRINC := "${@int(PRINC) + 1}"
#FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

#SRC_URI += "file://defconfig_norpc.patch"
#SRC_URI += "file://defconfig"

## Depend on fbset-modes if we have fbset support enabled
#python () {
#    try:
#        defconfig = bb.fetch2.localpath('file://defconfig', d)
#    except bb.fetch2.FetchError:
#        return
#
#    try:
#        configfile = open(defconfig)
#    except IOError:
#        return
#
#    if 'CONFIG_FBSET=y\n' in configfile.readlines():
#        d.setVar('FBSET_DEP', 'fbset-modes')
#}

#do_patch_append() {
#    #patch <${S}/defconfig_norpc.patch
#}

do_prepare_config_append() {
        #echo "DEBUG: do_prepare_config_append()"

	# FIXME: CodeBench Lite 2012.03-27-i686-pc-linux-gnu does not have rpc/rpc.h
        sed -i -e '/CONFIG_FEATURE_HAVE_RPC/d' ${S}/.config
        echo "# CONFIG_FEATURE_HAVE_RPC is not set" >> ${S}/.config
        sed -i -e '/CONFIG_MOUNT/d' ${S}/.config
        echo "# CONFIG_MOUNT is not set" >> ${S}/.config

	echo "DEBUG: Search for RPC in .config"
	grep -e "RPC" ${S}/.config
}
