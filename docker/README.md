# How to use fiware-testbed-deploy with Docker

Docker allows you to deploy an OFNIC container in a few minutes. This method requires that you have installed docker, see the [documentation](https://docs.docker.com/installation/) on how to do this.

Follow these steps:

1. Download [OFNIC' source code](https://github.com/FIWARE-UNIROMA1/FIWARE-OFNIC) from GitHub (`git clone https://github.com/FIWARE-UNIROMA1/FIWARE-OFNIC.git`)
2. `cd FIWARE-OFNIC/docker`
3. Using the command-line and within the directory you created type: `docker build -t fiware-ofnic .`.

After a few seconds you should have your OFNIC image created. Just run the command `docker images` and you see the list.

To execute the OFNIC image, execute the command `docker run fiware-ofnic`.

You can obtain the IP address of the machine just executing `docker-machine ip`.

If you want to stop the scenario you have to execute `docker ps`, take the Container ID and execute `docker stop cID` or `docker kill cID`.
