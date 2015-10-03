#!/bin/bash
cd /opt/ofnic/
echo "============STARTING OFNIC============="
sh ./opendaylight/run.sh &
java -jar ofnic-uniroma1-LATEST.jar