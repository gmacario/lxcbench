######################################################################################
#   STREAM-1.0.0								     #
######################################################################################
#This script run the download of the source of the Phoronix test Stream-1.0.0
#and the cross compilation to functioning on a ARM linux architecture.
#It require the test files (that can be downloaded on the target by the Phoronix Suit
#by the command "phoronix-test-suite download-test-files pts/stream") in the
#same folder of this script.

#!/bin/bash

unzip stream-1.1.0.zip
wget $(xpath -e "//URL" downloads.xml 2>/dev/null |  sed -e 's/<\/*URL>//g' -e 's/<URL>//g' -e 's/<\/URL>/ /g')
sudo ./install-stream.sh
./script-pts-xml.sh stream-1.1.0
