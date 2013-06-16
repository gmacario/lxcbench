PR = "${INC_PR}.17"

do_configure_append() {
	echo "DEBUG: do_configure_append()"

#        echo "" > ${S}/.config
#        CONF_SED_SCRIPT=""
#
#        kernel_conf_variable LOCALVERSION "\"${LOCALVERSION}\""
#        kernel_conf_variable LOCALVERSION_AUTO y
#
#        sed -e "${CONF_SED_SCRIPT}" < '${WORKDIR}/defconfig' >> '${S}/.config'
#
#        if [ "${SCMVERSION}" = "y" ]; then
#                # Add GIT revision to the local version
#                head=`git rev-parse --verify --short HEAD 2> /dev/null`
#                printf "%s%s" +g $head > ${S}/.scmversion
#        fi

cat <<END >>.config
# === Added by do_configure_append()
CONFIG_DEVPTS_MULTIPLE_INSTANCES=y
CONFIG_CGROUP_MEM_RES_CTLR=y
CONFIG_CGROUP_MEM_RES_CTLR_SWAP=y
CONFIG_VETH=y
CONFIG_MACVLAN=y
CONFIG_MACVTAP=y
CONFIG_VLAN_8021Q=y
CONFIG_VLAN_8021Q_GVRP=n
END

	echo "DEBUG: do_configure_append() exit"
}

# EOF
