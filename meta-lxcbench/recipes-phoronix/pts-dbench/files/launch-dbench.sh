#!/bin/sh

./dbench_/bin/dbench $@ -c client.txt > $LOG_FILE 2>&1
echo $? > ~/test-exit-status
