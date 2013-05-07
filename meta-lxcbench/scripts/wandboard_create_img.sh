#!/bin/sh

# Inspired by
# http://www.wandboard.org/index.php/forums?place=msg%2Fwandboard%2Fiz7QBZdJ-bE%2F1ePk3Nnb1BoJ

#set -x

SDCARD_FILE=~/tmp/sdcard.img
MOUNTPOINT=/tmp/sdcard

# First of all, you need to have 3 files :
# - "uImage" : kernel file.
# - "u-boot.imx" : uboot file.
# - Your root file system.
#
# The following script will create a "sdcard.img" file with your freshly built uImage and u-boot.imx files.
# You also need to provide a rootfs. In this case, it is a tar file "myrootfs.tar".

#UBOOT_FILE=~/wandboard/myandroid/out/target/product/wandboard/u-boot-6dl.bin
#UIMAGE_FILE=~/wandboard/myandroid/out/target/product/wandboard/uImage

UBOOT_FILE=~/wandboard/wandboard-sdk-20130329/precompiled/u-boot.bin-dual
UIMAGE_FILE=~/wandboard/wandboard-sdk-20130329/precompiled/uImage
ROOTFS_FILE=~/mel6-lxcbench/build-imx53qsb/tmp/deploy/images/core-image-lxcbench-imx53qsb.tar.bz2

echo "INFO: Creating ${SDCARD_FILE}"
mkdir -p `dirname ${SDCARD_FILE}`
dd bs=512 count=$(((1024*1024*1024)/512)) if=/dev/zero of=${SDCARD_FILE}
cat <<EOT | fdisk ${SDCARD_FILE}
o
n
p
1
8192

w
EOT

if [ "${UBOOT_FILE}" != "" ]; then
    if [ ! -e "${UBOOT_FILE}" ]; then
	echo "ERROR: Cannot find ${UBOOT_FILE}"
	exit 1
    fi
    echo "INFO: Adding ${UBOOT_FILE}"
    # Command to flash newer U-Boot (ver. 2013.xx):
    #dd if=${UBOOT_FILE} of=${SDCARD_FILE} conv=notrunc bs=512 seek=2
    # Command to flash old U-Boot (2009.xx):
    dd if=${UBOOT_FILE} of=${SDCARD_FILE} conv=notrunc bs=512 seek=2 skip=2
fi

if [ "${UIMAGE_FILE}" != "" ]; then
    if [ ! -e "${UIMAGE_FILE}" ]; then
	echo "ERROR: Cannot find ${UIMAGE_FILE}"
	exit 1
    fi
    echo "INFO: Adding ${UIMAGE_FILE}"
    dd if=${UIMAGE_FILE} conv=notrunc of=${SDCARD_FILE} bs=1M seek=1
fi

if [ "${ROOTFS_FILE}" != "" ]; then
    if [ ! -e "${ROOTFS_FILE}" ]; then
	echo "ERROR: Cannot find ${ROOTFS_FILE}"
	exit 1
    fi
    echo "INFO: Adding ${ROOTFS_FILE}"

    # Mount partition 1 of ${SDCARD_FILE} as loop0
    sudo losetup /dev/loop0 ${SDCARD_FILE} -o $((8192*512))
    sudo mkfs.ext3 /dev/loop0
    mkdir -p ${MOUNTPOINT}
    sudo mount /dev/loop0 ${MOUNTPOINT}

    #tar xf ${ROOTFS_FILE} -C ${MOUNTPOINT}
    bzip2 -dc ${ROOTFS_FILE} | sudo tar x -C ${MOUNTPOINT}

    sudo umount ${MOUNTPOINT}
    sudo losetup -d /dev/loop0
fi

ls -la ${SDCARD_FILE}
md5sum ${SDCARD_FILE}

cat <<EOT
# Once your ${SDCARD_FILE} file is created, you have to put it into your uSDHC - example:
#
# SDCARD_DEV=/dev/sdX
# sudo umount \${SDCARD_DEV}?
# sudo dd if=${SDCARD_FILE} of=\${SDCARD_DEV}

# At the first boot, stop autoboot and type the following U-Boot commands:
#
# setenv bootargs "console=ttymxc0,115200 root=/dev/mmcblk0p1 rootwait ro video=mxcfb0:dev=hdmi,1920x1080M@60,if=RGB24"
# setenv loadaddr 0x10800000
# setenv bootcmd 'mmc dev 2; mmc read \${loadaddr} 0x800 0x2200; bootm'
# saveenv
EOT

# === EOF ===
