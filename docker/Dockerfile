FROM ubuntu:14.04
EXPOSE 2222/tcp 6633/tcp 80/tcp 8080/tcp
VOLUME [/opt/ofnic/]                                                              
RUN apt-get -y update && apt-get install -y openjdk-7-jre xterm wget && mkdir /opt/ofnic/
RUN apt-get install unzip 
RUN wget https://github.com/FIWARE-UNIROMA1/FIWARE-OFNIC/releases/download/v2.1/FIWARE-OFNIC-v2.1.zip -P /opt/ofnic/
RUN unzip /opt/ofnic/FIWARE-OFNIC-v2.1.zip -d /opt/ofnic
RUN ls /opt
RUN chmod 775 /opt/ofnic/FIWARE-OFNIC-v2.1/start.sh  
RUN chmod 775 /opt/ofnic/FIWARE-OFNIC-v2.1/opendaylight/run.sh
CMD echo "========STARTING OFNIC==========
CMD /opt/ofnic/FIWARE-OFNIC-v2.1/opendaylight/run.sh
CMD sleep 60
CMD java -jar /opt/ofnic/FIWARE-OFNIC-v2.1/ofnic-uniroma1-v2.1.jar
