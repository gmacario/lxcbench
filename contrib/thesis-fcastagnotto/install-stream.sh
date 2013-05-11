######################################################################################
#   INSTALL STREAM-1.0.0                                                             #
######################################################################################
#This script run the cross-compilation of the Stream test.

#!/bin/sh

tar -zxvf stream-2009-04-11.tar.gz

arm-linux-gnueabi-gcc stream.c -O2 -fopenmp -o stream-bin
echo \$? > ~/test-exit-status

echo "#!/bin/sh
export OMP_NUM_THREADS=\$NUM_CPU_CORES
./stream-bin > \$LOG_FILE 2>&1
echo \$? > ~/test-exit-status" > stream

chmod +x stream
