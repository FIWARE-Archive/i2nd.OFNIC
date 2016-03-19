#<a name="top"></a>OFNIC

[![License badge](https://img.shields.io/badge/license-APACHE2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Documentation badge](https://readthedocs.org/projects/fiware-ofnic/badge/?version=latest)](https://fiware-ofnic.readthedocs.org/en/latest/)
[![Docker badge](https://img.shields.io/docker/pulls/fiware/ofnic.svg)](https://hub.docker.com/r/fiware/ofnic/)
[![Support badge]( https://img.shields.io/badge/support-sof-yellowgreen.svg)](http://stackoverflow.com/questions/tagged/fiware-ofnic)

* [Introduction](#introduction)
* [GEi overall description](#gei-overall-description)
* [Build and Install](#build-and-install)
* [Running](#running)
* [API Reference Documentation](#api-reference-documentation)
* [License](#license)
	
	  
## Introduction

This is the code repository for OFNIC GEri, the reference implementation of the Network Information & Control (NetIC) GE.
This project is part of [FIWARE](http://www.fiware.org) and of the [FIWARE Catalogue](http://catalogue.fiware.org/).
Any feedback on this documentation is highly welcome, including bugs, typos
or things you think should be included but aren't. You can use [github issues](https://github.com/FIWARE-UNIROMA1/FIWARE-OFNIC/issues/new) to provide any type of feedback.
You can find the Installation and Administration Guide and the User and Programmer's Guide on [readthedocs.org](https://fiware-ofnic.readthedocs.org/en/v2.0/)

[Top](#top)

## GEi overall description

OFNIC is a Software Defined Network (SDN) controller for OpenFlow-enabled networks. It enables the abstraction and virtualization of network resources and functionalities. It offers a RESTful interface in order to get information about the network topology components and elements either real or virtual. OFNIC also monitors the status of the network and provides near real-time data about network statistics with different levels of granularity (flow, node, port). Moreover it is capable of controlling the network forwarding capabilities and thus it is able to establish end-to-end paths with given requirements between any two nodes in the network.

[Top](#top)

## Getting OFNIC
You can get the last stable release [v2.0](https://github.com/FIWARE-UNIROMA1/FIWARE-OFNIC/releases/tag/v2.0), or the most updated under-development source code. 
For the first option you can download the [FIWARE-OFNIC_v2.0_src.zip](https://github.com/FIWARE-UNIROMA1/FIWARE-OFNIC/archive/v2.0.zip), for the second option you have to pull the code by cloning the controller repository on GitHub with the following commands: 
"git clone https://github.com/FIWARE-UNIROMA1/FIWARE-OFNIC.git"

[Top](#top)

## Build and Install

Build and Install documentation for OFNIC can be found at [the Installation and Administration Guide](https://fiware-ofnic.readthedocs.org/en/v2.0/Installation_and_administration_manual.html).

[Top](#top)

## Running

How to run OFNIC can be found at [the Installation and Administration Guide](https://fiware-ofnic.readthedocs.org/en/v2.0/Installation_and_administration_manual.html).

[Top](#top)

## API Reference Documentation

API reference documentation can be found at [NetIC Restful API Documetation] (http://docs.ofnic.apiary.io/)

[Top](#top)

## Testing

How to run tests can be found at [the Installation and Administration Guide](https://fiware-ofnic.readthedocs.org/en/v2.0/Installation_and_administration_manual.html)

[Top](#top)

## License

OFNIC is licensed under [Apache License Version 2](https://www.apache.org/licenses/LICENSE-2.0).

[Top](#top)
