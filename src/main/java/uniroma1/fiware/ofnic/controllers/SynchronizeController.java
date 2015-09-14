package uniroma1.fiware.ofnic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.controller.hosttracker.northbound.HostConfig;
import org.opendaylight.controller.hosttracker.northbound.Hosts;
import org.opendaylight.controller.switchmanager.northbound.NodeConnectorProperties;
import org.opendaylight.controller.topology.northbound.Topology;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uniroma1.fiware.ofnic.data.TempData;
import uniroma1.fiware.ofnic.exceptions.BadRequestException;
import uniroma1.fiware.ofnic.exceptions.InternalErrorException;
import uniroma1.fiware.ofnic.model.Host;
import uniroma1.fiware.ofnic.model.NodeConnectorPropertiesWrapper;
import uniroma1.fiware.ofnic.model.Port;
import uniroma1.fiware.ofnic.model.response.GetNetworkNodePortResponse;
import uniroma1.fiware.ofnic.model.response.GetNetworkNodeResponse;
import uniroma1.fiware.ofnic.model.response.GetNetworkResponse;
import uniroma1.fiware.ofnic.services.OdlServices;
import uniroma1.fiware.ofnic.services.OdlUtilServices;
import uniroma1.fiware.ofnic.utils.Utils;



@RequestMapping("/netic.v1/OFNIC/synchronize")
@RestController
public class SynchronizeController {



    @RequestMapping(value = "/network", method = RequestMethod.GET)
    public String getNetwork() {
    	Topology topology = OdlServices.getNetworkTopology();
    	if(topology!=null){
    		List<String> nodes = OdlUtilServices.getNodeFromTopology(topology);
    		List<String> paths = new ArrayList<String>();
    		for(String s : TempData.installedFlow.keySet())
    			paths.add(s);
    		List<String> hosts = OdlUtilServices.getHostsList();
    		return Utils.parseJsonToString(new GetNetworkResponse(paths, nodes, hosts));
    	}
    	else
    		throw new InternalErrorException("Internal Error");
    }


    @RequestMapping(value = "/network/node", method = RequestMethod.GET)
    public String getNetworkNodes() {
    	Topology topology = OdlServices.getNetworkTopology();
    	if(topology!=null){
    		List<String> nodes = OdlUtilServices.getNodeFromTopology(topology);
    		return Utils.parseJsonToString(nodes);
    	}
    	else
    		throw new InternalErrorException("Internal Error");
    }

    @RequestMapping(value = "/network/host", method = RequestMethod.GET)
    public String getNetworkHosts() {
    	Topology topology = OdlServices.getNetworkTopology();
    	if(topology!=null){
    		List<String> hosts = OdlUtilServices.getHostsList();
    		return Utils.parseJsonToString(hosts);
    	}
    	else
    		throw new InternalErrorException("Internal Error");
    }


    @RequestMapping(value = "/network/node/{id}", method = RequestMethod.GET)
    public String getNetworkNode(@PathVariable String id) {
    	NodeConnectorPropertiesWrapper ncpw = OdlServices.getOFNode(id);
    	List<Port> ports = new ArrayList<Port>();
    	if(ncpw!=null){
    		for(NodeConnectorProperties ncp : ncpw.getNodeConnectorProperties()){
    			if(ncp.getNodeconnector()!=null)
    				ports.add(new Port(ncp.getNodeconnector().getNodeConnectorIDString(), ncp.getNodeconnector().getType()));
    		}
    		return Utils.parseJsonToString(new GetNetworkNodeResponse(ports));
    	}
    	else{
    		throw new BadRequestException("Error fetching node");
    	}
    }





    @RequestMapping(value = "/network/node/{nodeId}/port/{portId}", method = RequestMethod.GET)
    public String getNetworkNodePort(@PathVariable String nodeId, @PathVariable String portId) {
    	GetNetworkNodePortResponse gnpr = OdlServices.getNodePortProperties(nodeId, portId);
    	if(gnpr==null)
    		throw new BadRequestException("Node id or port id not found");
    	return Utils.parseJsonToString(gnpr);
    }




    @RequestMapping(value = "/network/host/{hostIp}", method = RequestMethod.GET)
    public String getNetworkHost(@PathVariable String hostIp) {
    	Hosts hosts = OdlServices.getAllHosts();
    	if(hosts!=null){
    		for(HostConfig h : hosts.getHostConfig()){
    			if(h.getNetworkAddress().toString().equals(hostIp)){
    				Host res = new Host(h.getDataLayerAddress(), h.getNetworkAddress(), h.getNodeId(), h.getNodeConnectorId());
    				return Utils.parseJsonToString(res);
    			}
    		}

    	}
    	throw new BadRequestException("Error fetching host");
    }


}