Welcome to FIWARE OFNIC GE's documentation!
==============================================================

.. _here: http://forge.fiware.org/plugins/mediawiki/wiki/fiware/index.php/FIWARE.OpenSpecification.I2ND.NetIC_R4
.. _NetIC_RESTful_API: http://forge.fiware.org/plugins/mediawiki/wiki/fiware/index.php/FIWARE.OpenSpecification.I2ND.NetIC_R4

Available manuals:

* User-and-Programmers_ manual.
* Installation-and-Administration_ manual.



-----------------------------------------------------------------

.. _User-and-Programmers:

User & Programmers
==============================================================
Introduction
-----------------------------------------------------------------

OFNIC provides a programmable interface to control and retrieve information and control Openflow Networks. The interface is based on the RESTful paradigm and is powered by a Web Server developed in Python programming language. OFNIC relies on the Openflow protocol in order to abstract and virtualize forwarding capabilities of the Openflow Network. This GEi is an extension to the OpenDaylight Openflow controller.

OFNIC is composed of many modules which communicate whith each other with events. It is designed with an event-driven paradigm also to manage information that comes from the network. The OFNIC RESTful interface is an instance of the NetIC API ( NetIC Generic Enabler Open Specifications can be found here_). 

Goal of this document is to provide a useful guide for developers who want to build new applications based on the NetIC API, and a guide for users that might utilize the provided GUI application. Specific sections are dedicated to developer tools intended to help them during development, test and deployment.

User Guide
==============================================================

The NetIC_RESTful_API_ reference provides a page which describes how to use this API in details. It includes a set of commands, which can be used to manipulate and instantiate network resources physical or virtual. Since NetIC RESTful API is based on the HTTP protocol there are a rich variety of tools, which can be used to access the OFNIC GEi interface. 
The sections of the Programmer Guide will go into more detail on how to use and develop against NetIC RESTful API.

OFNIC GUI
-----------------------------------------------------------------

The OFNIC GEi is released with a GUI feature. The NetIC API users might utilize the GUI to navigate the exposed RESTful webservices of the GEi. The GUI has been developed in Javascript language, and uses the FIWARE site template. The web GUI comes embedded in the OFNIC source package release, see the OFNIC Installation-and-Administration_ guide to download the package. 

The GUI relies on the OFNIC GEi, it should up and running correctly. To access the GUI from a web browser navigate to: http://localhost/gui

The GUI page that appears is shown in the figure below.

.. image:: OFNIC_GUI_a.png

INSERT PICTURE HERE!!!________________________________________________________________

The default username is 'admin' and can be authenticated with the password 'admin'. After logging in, one can note that the GUI is composed of four navigation tabs: Synchronization,Statistics, Routing, and Access Control. The Synchronization tab shows the network topology with an interactive diagram. Network nodes are clickable and additional information is shown if a node is selected. For example the number of Actions or Ports. Moreover the Displayed ports are clickable and show to which other network entity is the selected node connected to.

The Statistics navigation tab, concerns about the presentation of network statistics collected by the OFNIC GEi. The dynamic network topology is present so the user can select nodes he might want to monitor from the graph. The Statistics tab is shown in the figure below.

INSERT PICTURE HERE!!!________________________________________________________________

The presence of the Openflow network is not mandatory, if no network is attached to the OFNIC GEi, the following warning will appear on the GUI: "no network nodes detected"

Programmer Guide
==============================================================

This section describes the way a programmer can interact ith the OFNIC GEi. It is assumed that the OFNIC GEi is up and running. See the OFNIC Installation-and-Administration_ guide for this.

Navigation with browser
-----------------------------------------------------------------

The root path of the Web Server is:

    https://127.0.0.1/netic.v1/doc

This page shows all commands that can be sent to the REST API of OFNIC. 

Note that not all the methods and commands might be supported directly from the browser, that is because some browser does not support natively the DELETE Http command.

Regarding POST command the parameters of the request might be:
encoded in the url

    *field1=value1&field2=value2&field3=value3...*
 
formatted as JSON objects in the body of the Http Request.

    *{field1 : value1, field2 : value2, field3 : value3}*


Examples
-----------------------------------------------------------------

In this example the OFNIC GEi is connected to an Openflow network composed of three Openflow nodes. The nodes run Openvswitch for implementing the Openflow protocol and SNMPd and SNMP Sub-Agent to collect statistics. The OFNIC GEi is located at the same machine from which the browser is used. Using the browser, type in the location bar the following URL string and then press Enter.

    https://127.0.0.1/netic.v1/OFNIC/synchronize/network

The result displayed, should be the list of nodes present in the network:

    *{"resultCode": 0, "displayError": "No error", "result": {"Paths": [], "Nodes": [1, 2, 3]}}*

In order to retrieve information about the configuration of node with ID equal to 2, the following string should be typed in the URL container:

    https://127.0.0.1/netic.v1/OFNIC/synchronize/network/node/2

The following JSON response should appear in the Web browser:

    *{"resultCode": 0, "displayError": "No error", "result": {"Port_Names": ["eth3", "eth2", "br0", "eth1"], "Port_Index": [3, 2, 65534, 1], "Num_Buffers": 256, "Actions": 4095, "Num_Tables": 255}}*

The same operation can be repeated to get information about port 1 of node 2, with the following URL:

    https://localhost:2222/netic.v1/OFNIC/synchronize/network/node/2/port/1

the following result is expected:

    *{"resultCode": 0, "displayError": "No error", "result": {"Active": true, "Config": 0, "State": 0, "Speed": 0, "links": [0]}}*

To retrieve statistics about a port of a certain node (for example port 1 of node 2) the following URL might be used:

    https://localhost:2222/netic.v1/OFNIC/statistics/node/2/port/1

and the results that appears in the browser shows the transmitted and received bytes of the specified port:

    *{"resultCode": 0, "displayError": "No error", "result": {"Tx_bytes": 34, "Rx_bytes": 20}}*

As one can note from the examples, all response bodies are in JSON format and three fields are always present:
* resultCode: 0 for no errors, any other number for the occuring error.
* displayError: a string that displays the type of error
* result: the result of the request.


-----------------------------------------------------------------

.. _Installation-and-Administration:

Installation & Administration
==============================================================

Goal of the document
==============================================================

The OFNIC is an implementation of the NetIC Generic Enabler Open Specifications. This GEi is in charge of providing a common programmable interface to an Openflow Network, by collecting information and statistics regarding the managed Openflow Network. This interface is based on the NetIC Open API RESTful specifications. The OFNIC GEi is an extension of the open-source OpenDaylight Controller [1]. It relies on the Openflow protocol to retrieve network information about the managed network. The OFNIC GEi provides also a Graphical User Interface (GUI) based on web technologies. Basically this is a web page with javascript code that communicates with the RESTful interface of the GEi. 

Goal of this document is to provide a useful guide for the installation of OFNIC GEi, together with its GUI. The document starts describing basic software and hardware required to support the OFNIC GEi on top of a device. It then follows with specific technical information that might help users and administrators: running processes, diagnosis tests, network flows etc.

OFNIC GEi
==============================================================

Software and Hardware environment
-------------------------------------------------------------

The OFNIC OpenFlow controller runs in a Java Virtual Machine. Being a Java application, it can (potentially) runs on any machine that supports Java. However, all the software have been tested on recent Linux distributions, so we recommend the following:

* A recent Linux distribution (for example Ubuntu 14.04 LTS  or Debian 7.x)
* Java Virtual Machine 1.7

Prerequisites
-------------------------------------------------------------

* As the previous paragraph, being a Java application, the only requistite is Java Virtual Machine.

Getting OFNIC
-------------------------------------------------------------

There are two options for obtaining the OFNIC Controller. The first option is to download the pre-built current build. The second option is to getting the source code of the component and build the code on your machine.

The pre-built package can be downloaded at TBD

    https://github.com/FIWARE-UNIROMA1/FIWARE-OFNIC

Another way of getting the source code is to pull the code by cloning the controller repository on GitHub with the following commands:

    git clone https://github.com/FIWARE-UNIROMA1/FIWARE-OFNIC.git


Build the code
-------------------------------------------------------------

Prerequisites
-------------------------------------------------------------

The following are required for building the codebase:

* Maven 3.x.y

If you use a Debian or Ubuntu machine, you can install Maven with the following command:

    *sudo apt-get install maven*

and check the installed version with:

    *mvn -v*


Using a system shell locate in the main OFNIC source code directory, where is located the pom.xml maven’s configuration file. Run the following commands:

    *mvn clean install*

Configuration
-------------------------------------------------------------

qwertyqwertyqwert
qwertqwertyqwert
qwertqwertywertyu

Running
-------------------------------------------------------------
Now OFNIC GEi is ready to start running. With a terminal locate in the main folder with the following content:

* opendaylight build folder
* ofnic.conf
* ofnic-uniroma1.jar
* qos_queue.con
* run.sh

The command reported below starts the OFNIC controller:
    *./run.sh*













