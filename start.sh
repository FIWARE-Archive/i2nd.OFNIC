#!/bin/bash
echo "============STARTING OFNIC============="
sh /opt/ofnic/opendaylight/run.sh &
echo "Waiting Opendaylight starts..."
sleep 120
java -jar ./ofnic-uniroma1-LATEST.jar
