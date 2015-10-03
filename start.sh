#!/bin/bash
if[ ! -f /opt/ofnic/ofnic-uniroma1-LATEST.jar ]
then
    echo "============Initialising Data============"
    cp ofnic-uniroma1-LATEST.jar /opt/ofnic/ofnic-uniroma1-LATEST.jar
    cp -r opendaylight /opt/ofnic/
    echo "Copied OpenDaylight to /opt/ofnic"
    echo "Copied ofnic-uniroma1-LATEST.jar to /opt/ofnic"
fi
cd /opt/ofnic/
echo "============STARTING OFNIC============="
sh ./opendaylight/run.sh &
java -jar ofnic-uniroma1-LATEST.jar