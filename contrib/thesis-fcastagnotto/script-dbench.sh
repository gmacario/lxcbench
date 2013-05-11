######################################################################################
#   DBENCH-1.0.0								     #
######################################################################################
#This script run the download of the source of the Phoronix test Dbench-1.0.0
#and the cross compilation to functioning on a ARM linux architecture.
#It require the test files (that can be downloaded on the target by the Phoronix Suit
#by the command "phoronix-test-suite download-test-files pts/dbench") in the
#same folder of this script.

#!/bin/bash

unzip dbench-1.0.0.zip
wget $(xpath -e "//URL" downloads.xml 2>/dev/null |  sed -e 's/<\/*URL>//g' -e 's/<URL>//g' -e 's/<\/URL>/ /g')
sudo ./install-dbench.sh
./script-pts-xml.sh dbench-1.0.0
