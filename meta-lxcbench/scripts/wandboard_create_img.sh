#!/bin/sh

# From http://www.wandboard.org/index.php/forums?place=msg%2Fwandboard%2Fiz7QBZdJ-bE%2F1ePk3Nnb1BoJ
#
# First of all, you need to have 3 files :
# - "uImage" : kernel file.
# - "u-boot.imx" : uboot file.
# - Your root file system.
# 
# The following script will create a "sdcard.img" file with your freshly built uImage and u-boot.imx files.
# You also need to provide a rootfs. In this case, it is a tar file "myrootfs.tar".

dd bs=512 count=$(((1024*1024*1024)/512))if=/dev/zero of=sdcard.img
cat <<EOF | fdisk sdcard.img
o
n
p
1
8192

w
EOF
losetup /dev/loop0 sdcard.img -o $((8192*512))
mkfs.ext3 /dev/loop0
mount /dev/loop0 /mnt/sdcard

tar xf myrootfs.tar -C /mnt/sdcard
umount /mnt/sdcard
losetup -d /dev/loop0

dd bs=1M seek=1 conv=notrunc if=uImage of=sdcard.img
dd bs=512 seek=2 conv=notrunc if=u-boot.imx of=sdcard.img


# Once your "sdcard.img" file is created, you have to put it into your sdcard (using dd).
# At the first boot, go into uboot and change your environment variables like this :

# setenv bootargs "console=ttymxc0,115200 root=/dev/mmcblk0p1 rootwait ro video=mxcfb0:dev=hdmi,1920x1080M@60,if=RGB24"
# setenv loadaddr 0x10800000
# setenv bootcmd "mmc dev 0; mmc read ${loadaddr} 0x800 0x2200; bootm"

# === EOF ===
