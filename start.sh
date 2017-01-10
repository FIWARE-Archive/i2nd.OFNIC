#!/bin/bash
echo "============STARTING OFNIC============="
./opendaylight/run.sh &
echo "Waiting Opendaylight starts..."
sleep 120
java -jar ./ofnic-uniroma1-LATEST.jar
