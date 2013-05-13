#This script generates the pts-install.xml file, needed by the Phoronix Suite to correctly run the tests.
#The generated file must be copied inside the folder of the test "$HOME/.phoronix-test-suite/installed-tests/pts/<name_test>/"
#It assumes to default that the user on the target system is "debian": if not, change it in the "href" field.

#!/bin/bash

if [ -z $1 ];
then
	echo "ERROR: missing parameter (name of test)"
else

echo "<?xml version="1.0"?>
<!--Phoronix Test Suite v4.4.1 (Forsand)-->
<?xml-stylesheet type="text/xsl" href="file:////home/debian/.phoronix-test-suite/xsl/pts-test-installation-viewer.xsl"?>
<PhoronixTestSuite>
  <TestInstallation>
    <Environment>
      <Identifier>pts/$1</Identifier>
      <Version>1.0.0</Version>
      <CheckSum>dc383f29025b00a6cad1f825d2b87b99</CheckSum>
      <CompilerData>{"compiler-type":"CC","compiler":"gcc","compiler-options":"-lpopt"}</CompilerData>
      <SystemIdentifier>QVJNdjcgcmV2IDVfX0ZyZWVzY2FsZSBpLk1YNTMgKERldmljZSBUcmVlIFN1cHBvcnQpX19EZWJpYW4gNi4wLjdfX0dDQyA0LjQuNQ==</SystemIdentifier>
    </Environment>
    <History>
      <InstallTime>2013-05-03 16:11:35</InstallTime>
      <InstallTimeLength>139</InstallTimeLength>
      <LastRunTime>0000-00-00 00:00:00</LastRunTime>
      <TimesRun>0</TimesRun>
      <AverageRunTime></AverageRunTime>
      <LatestRunTime></LatestRunTime>
    </History>
  </TestInstallation>
</PhoronixTestSuite>

" > pts-install.xml
chmod 0777 pts-install.xml

fi
