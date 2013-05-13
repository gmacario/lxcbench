######################################################################################
#   DBENCH-1.0.0								     #
######################################################################################

#This script run the download of the source of the Phoronix test Dbench-1.0.0
#and the cross compilation to functioning on a ARM linux architecture.

#It require the ZIP test file "dbench-1.0.0.zip" in the same folder of this script.
#(The file can be downloaded on the target using the Phoronix Suite by the command
#"phoronix-test-suite download-test-files pts/dbench", and then can be found inside
#the folder "$HOME/.phoronix-test-suite/openbenchmarking.org/pts/")

#!/bin/bash

unzip dbench-1.0.0.zip
wget $(xpath -e "//URL" downloads.xml 2>/dev/null |  sed -e 's/<\/*URL>//g' -e 's/<URL>//g' -e 's/<\/URL>/ /g')
sudo ./install-dbench.sh
./script-pts-xml.sh dbench-1.0.0
