#!/bin/bash
cd /opt/ofnic/
echo "============STARTING OFNIC============="
./opendaylight/run.sh &
java -jar ofnic-uniroma1-LATEST.jar