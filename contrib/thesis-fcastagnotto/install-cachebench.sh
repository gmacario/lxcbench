######################################################################################
#   INSTALL CACHEBENCH-1.0.0                                                         #
######################################################################################
#This script run the cross-compilation of the Cachebench test.

#!/bin/sh

tar -zxvf llcbench.tar.gz
cd llcbench/

sed -i "s/gcc/arm-linux-gnueabi-gcc/g" ./conf/sys.linux-mpich

make linux-mpich
make cache-bench

echo $? > ~/install-exit-status
cd ..

echo "#!/bin/sh
cd llcbench/cachebench/
./cachebench \$@ > \$LOG_FILE" > cachebench

chmod +x cachebench
