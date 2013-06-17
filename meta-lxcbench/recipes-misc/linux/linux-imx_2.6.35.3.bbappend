PR = "${INC_PR}.17"

do_configure_append() {
	echo "DEBUG: do_configure_append()"

cat <<END >>.config
# Kernel configuration fragment for supporting LXC
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
