######################################################################################
#   INSTALL DBENCH-1.0.0                                                             #
######################################################################################
#This script run the cross-compilation of the Dbench test.

#!/bin/sh

tar -zxvf dbench-4.0.tar.gz
mkdir $HOME/dbench_/
cd dbench-4.0/

./autogen.sh
./configure -build=i686-linux --host=arm-linux-gnueabi --prefix=$HOME/dbench_/

make -j $NUM_CPU_JOBS
echo $? > ~/install-exit-status
make install
cp client.txt ../
cd ..
rm -rf dbench-4.0/

echo "#!/bin/sh
./dbench_/bin/dbench \$@ -c client.txt > \$LOG_FILE 2>&1
echo \$? > ~/test-exit-status" > dbench

chmod +x dbench
