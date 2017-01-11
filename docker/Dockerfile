FROM ubuntu:14.04
EXPOSE 2222/tcp 6633/tcp 80/tcp 8080/tcp
#VOLUME [/opt/ofnic/]                                                              
RUN apt-get -y update && apt-get install -y openjdk-7-jre xterm wget unzip && mkdir ~/ofnic/ 
RUN wget https://github.com/FIWARE-UNIROMA1/FIWARE-OFNIC/releases/download/v2.1/FIWARE-OFNIC-v2.1.zip -P ~/ofnic/
RUN unzip ~/ofnic/FIWARE-OFNIC-v2.1.zip -d /opt/ofnic
RUN cd  ~
RUN chmod 775 ~/ofnic/FIWARE-OFNIC-v2.1/start.sh  
RUN chmod 775 ~/ofnic/FIWARE-OFNIC-v2.1/opendaylight/run.sh
CMD echo "========STARTING OFNIC==========
CMD ~/ofnic/FIWARE-OFNIC-v2.1/opendaylight/run.sh -start
CMD sleep 120 && java -jar ~/ofnic/FIWARE-OFNIC-v2.1/ofnic-uniroma1-LATEST.jar
