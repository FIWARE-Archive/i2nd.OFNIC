package uniroma1.fiware.ofnic.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opendaylight.controller.connectionmanager.northbound.Nodes;
import org.opendaylight.controller.forwardingrulesmanager.FlowConfig;
import org.opendaylight.controller.hosttracker.northbound.HostConfig;
import org.opendaylight.controller.hosttracker.northbound.Hosts;
import org.opendaylight.controller.sal.core.Node;
import org.opendaylight.controller.statistics.northbound.FlowStatistics;
import org.opendaylight.controller.statistics.northbound.PortStatistics;
import org.opendaylight.controller.topology.northbound.EdgeProperties;
import org.opendaylight.controller.topology.northbound.Topology;

import uniroma1.fiware.ofnic.data.Constants;
import uniroma1.fiware.ofnic.data.TempData;
import uniroma1.fiware.ofnic.model.FlowConfiguration;
import uniroma1.fiware.ofnic.model.Link;
import uniroma1.fiware.ofnic.model.NodeConnectorPropertiesWrapper;
import uniroma1.fiware.ofnic.model.Path;
import uniroma1.fiware.ofnic.model.StaticFlowConfiguration;
import uniroma1.fiware.ofnic.model.response.GetNetworkNodePortResponse;
import uniroma1.fiware.ofnic.utils.Utils;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;


public class OdlServices {


    public static Node getNode(String nodeId){
    	Nodes nodes = getAllNodes();
    	for(Node node: nodes.getNode()){
    		if(node.getId().equals(nodeId))
    			return node;
    	}
    	return null;
    }



    public static Nodes getAllNodes(){
    	Nodes nodes;
		try {
			WebResource res = TempData.client.resource(Constants.odlGetAllNodesWSPath);
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			for(NewCookie nc : TempData.odlCookies)
				builder.cookie(nc);
			nodes = builder.get(Nodes.class);
		}
		catch (ClientHandlerException e) {
			TempData.logger.error(e.getMessage());
			return null;
		}
		return nodes;
	}




	public static Topology getNetworkTopology() {
		Topology topology;
		try {
			WebResource res = TempData.client.resource(Constants.odlGetNetworkTopology);
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			for(NewCookie nc : TempData.odlCookies)
				builder.cookie(nc);
			topology = builder.get(Topology.class);
			if(topology != null)
				TempData.lastKnownTopology = topology;
		}
		catch (ClientHandlerException e) {
			TempData.logger.error(e.getMessage());
			return TempData.lastKnownTopology;
		}
		return topology;
	}



	public static int installStaticFlow(String nodeId, StaticFlowConfiguration sfc){
		sfc.setDp(nodeId);
		FlowConfig fc = new FlowConfig();
		fc.setInstallInHw("true");
		fc.setName(new Integer(new Random().nextInt(100000000)).toString());
		sfc.setName(fc.getName());
		Node n = new Node();
		n.setType("OF");
		n.setId(nodeId);
		fc.setNode(n);
		fc.setIngressPort(sfc.getInput_port());
		if(sfc.getPriority().equals("")){
			fc.setPriority(Constants.BASE_FLOW_PRIORITY.toString());
			sfc.setPriority(Constants.BASE_FLOW_PRIORITY.toString());
		}
		else
			fc.setPriority(sfc.getPriority());
		if(sfc.getEtherType().equals("")){
			fc.setEtherType("0x800");
			sfc.setEtherType("0x800");
		}
		else
			fc.setEtherType(sfc.getEtherType());
		fc.setNwSrc(sfc.getNw_src());
		fc.setNwDst(sfc.getNw_dst());
		List<String> acts = new ArrayList<String>();
		if(sfc.getBandwidth()==null || sfc.getBandwidth().equals(""))
			acts.add("OUTPUT="+sfc.getOutput_port());
		else{
			int enqueuePort = Utils.getMatchingQueue(sfc.getBandwidth());
			acts.add("ENQUEUE="+sfc.getOutput_port()+":"+enqueuePort);
		}
		fc.setActions(acts);
		if(!sfc.getDl_src().equals(""))
			fc.setDlSrc(sfc.getDl_src());
		if(!sfc.getDl_dst().equals(""))
			fc.setDlDst(sfc.getDl_dst());
		if(!sfc.getTp_src().equals(""))
			fc.setTpSrc(sfc.getTp_src());
		if(!sfc.getTp_dst().equals(""))
			fc.setTpDst(sfc.getTp_dst());
		if(!sfc.getTosBits().equals(""))
			fc.setTosBits(sfc.getTosBits());
		if(!sfc.getProtocol().equals(""))
			fc.setProtocol(sfc.getProtocol());
		int r = pushFlowWithPut(Utils.parseJsonToString(fc), fc.getNode().getId(), fc.getName());
		if(r<0)
			return r;
		TempData.installedStaticFlow.add(sfc);
		return r;
	}


	public static int installPathFlows(Path path, HostConfig srcHostConfig, HostConfig dstHostConfig, FlowConfiguration flowConfiguration){
		ArrayList<FlowConfig> installedPathFlows = new ArrayList<FlowConfig>();

		//First hop flow
		FlowConfig fc = new FlowConfig();
		fc.setInstallInHw("true");
		fc.setName(new Integer(new Random().nextInt(100000000)).toString());
		Node n = new Node();
		n.setType("OF");
		n.setId(srcHostConfig.getNodeId());
		fc.setNode(n);
		fc.setIngressPort(srcHostConfig.getNodeConnectorId());
		fc.setPriority(Constants.BASE_FLOW_PRIORITY.toString());
		fc.setEtherType("0x800");
		fc.setNwSrc(srcHostConfig.getNetworkAddress());
		fc.setNwDst(dstHostConfig.getNetworkAddress());

		List<String> acts = new ArrayList<String>();
		if(flowConfiguration.getBandwidth()==null || flowConfiguration.getBandwidth().equals(""))
			acts.add("OUTPUT="+path.getEdgeList().get(0).headPort);
		else{
			int enqueuePort = Utils.getMatchingQueue(flowConfiguration.getBandwidth());
			acts.add("ENQUEUE="+path.getEdgeList().get(0).headPort+":"+enqueuePort);
		}
		fc.setActions(acts);

		if(!flowConfiguration.getDl_src().equals(""))
			fc.setDlSrc(flowConfiguration.getDl_src());
		if(!flowConfiguration.getDl_dst().equals(""))
			fc.setDlDst(flowConfiguration.getDl_dst());
		if(!flowConfiguration.getTp_src().equals(""))
			fc.setTpSrc(flowConfiguration.getTp_src());
		if(!flowConfiguration.getTp_dst().equals(""))
			fc.setTpDst(flowConfiguration.getTp_dst());
		if(!flowConfiguration.getProtocol().equals(""))
			fc.setProtocol(flowConfiguration.getProtocol());
		int r = pushFlowWithPut(Utils.parseJsonToString(fc), fc.getNode().getId(), fc.getName());
		if(r<0)
			return r;
		installedPathFlows.add(fc);

		int index = 0;
		for(Link l: path.getEdgeList()){
			String outputPort = "";
			if(index == path.getEdgeList().size()-1)
				outputPort = dstHostConfig.getNodeConnectorId();
			else
				outputPort = path.getEdgeList().get(index+1).headPort;
			fc = new FlowConfig();
			fc.setInstallInHw("true");
			fc.setName(new Integer(new Random().nextInt(100000000)).toString());
			n = new Node();
			n.setType("OF");
			n.setId(l.tailNode);
			fc.setNode(n);
			fc.setIngressPort(l.tailPort);
			fc.setPriority(Constants.BASE_FLOW_PRIORITY.toString());
			fc.setEtherType("0x800");
			fc.setNwSrc(srcHostConfig.getNetworkAddress());
			fc.setNwDst(dstHostConfig.getNetworkAddress());
			acts = new ArrayList<String>();
			if(flowConfiguration.getBandwidth()==null || flowConfiguration.getBandwidth().equals(""))
				acts.add("OUTPUT="+outputPort);
			else{
				int enqueuePort =  Utils.getMatchingQueue(flowConfiguration.getBandwidth());
				acts.add("ENQUEUE="+outputPort+":"+enqueuePort);
			}
			fc.setActions(acts);
			if(!flowConfiguration.getDl_src().equals(""))
				fc.setDlSrc(flowConfiguration.getDl_src());
			if(!flowConfiguration.getDl_dst().equals(""))
				fc.setDlDst(flowConfiguration.getDl_dst());
			if(!flowConfiguration.getTp_src().equals(""))
				fc.setTpSrc(flowConfiguration.getTp_src());
			if(!flowConfiguration.getTp_dst().equals(""))
				fc.setTpDst(flowConfiguration.getTp_dst());
			if(!flowConfiguration.getProtocol().equals(""))
				fc.setProtocol(flowConfiguration.getProtocol());
			r = pushFlowWithPut(Utils.parseJsonToString(fc), fc.getNode().getId(), fc.getName());
			if(r<0)
				return r;
			installedPathFlows.add(fc);
			index++;
		}
		TempData.installedFlow.put(path.getPathName(), installedPathFlows);
		return 0;
	}




	private static int pushFlowWithPut(String flowConfig, String nodeId, String flowName){
		//TempData.logger.info(flowConfig);
		try {
			WebResource res = TempData.client.resource(Constants.odlAddFlow+"/"+nodeId+"/staticFlow/"+flowName);
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			String r = builder.put(String.class, flowConfig);
		}
		catch (RuntimeException e) {
			TempData.logger.error(e.getMessage());
			return -1;
		}
		return 0;
	}



	private static int pushFlowWithPost(String flowConfig, String nodeId, String flowName){
		TempData.logger.info(flowConfig);
		try {
			WebResource res = TempData.client.resource(Constants.odlAddFlow+"/"+nodeId+"/staticFlow/"+flowName);
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			String r = builder.post(String.class, flowConfig);
		}
		catch (RuntimeException e) {
			TempData.logger.error(e.getMessage());
			return -1;
		}
		return 0;
	}


	public static Hosts getAllHosts(){
		Hosts hosts;
		try {
			WebResource res = TempData.client.resource(Constants.odlGetAllHostConfiguration);
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			for(NewCookie nc : TempData.odlCookies)
				builder.cookie(nc);
			hosts = builder.get(Hosts.class);
			TempData.logger.info(hosts.getHostConfig().toArray()[1].toString());
		}
		catch (RuntimeException e) {
			return null;
		}
		return hosts;
	}



	public static PortStatistics getAllNodePortStatistics(String nodeId){
		PortStatistics portStatistics;
		try {
			WebResource res = TempData.client.resource(Constants.odlGetAllPortsStatistics+nodeId);
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			for(NewCookie nc : TempData.odlCookies)
				builder.cookie(nc);
			portStatistics = builder.get(PortStatistics.class);
		}
		catch (RuntimeException e) {
			return null;
		}
		return portStatistics;
	}




	public static HostConfig getHostConfiguration(String hostIp){
		HostConfig hostConfig;
		try {
			WebResource res = TempData.client.resource(Constants.odlGetHostConfiguration+"/"+hostIp);
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			hostConfig = builder.get(HostConfig.class);
		}
		catch (RuntimeException e) {
			return null;
		}
		return hostConfig;
	}



	public static int removeStaticFlows(String flowId) {
		for(StaticFlowConfiguration f : TempData.installedStaticFlow){
    		if(flowId.equals(f.getName())){
    			try {
    				//logger.info(f.getNode().getId()+" - "+f.getName());
    				WebResource res = TempData.client.resource(Constants.odlRemoveFlow+f.getDp()+"/staticFlow/"+f.getName());
    			Builder builder = res.type(MediaType.APPLICATION_JSON);
    			ClientResponse response = builder.delete(ClientResponse.class);
    			TempData.logger.info("Flow removed: "+response.getStatus()+" "+response.getClientResponseStatus());
    			TempData.installedStaticFlow.remove(f);
    			return 0;
    			}
    			catch (RuntimeException e) {
    				System.out.println(e.getMessage());
    				return -1;
    			}
    		}
    		}
		return -1;
	}



	public static void removePathFlows(Path p) {
		for(FlowConfig f : TempData.installedFlow.get(p.getPathName())){
			try {
				//logger.info(f.getNode().getId()+" - "+f.getName());
				WebResource res = TempData.client.resource(Constants.odlRemoveFlow+f.getNode().getId()+"/staticFlow/"+f.getName());
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			ClientResponse response = builder.delete(ClientResponse.class);
			TempData.logger.info("Flow removed: "+response.getStatus()+" "+response.getClientResponseStatus());
			}
			catch (RuntimeException e) {
				System.out.println(e.getMessage());
			}
		}
	}





	public static FlowStatistics getAllNodeFlowStatistics(String nodeId){
		FlowStatistics fStatistics;
		try {
			WebResource res = TempData.client.resource(Constants.odlGelNodeFlowsStatistics+nodeId);
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			fStatistics = builder.get(FlowStatistics.class);
			TempData.logger.info(fStatistics.toString());
		}
		catch (RuntimeException e){
			TempData.logger.error(e.getMessage());
			TempData.logger.error(e.getCause());
			TempData.logger.error(e.getStackTrace());
			return null;
		}
		return fStatistics;
	}




	public static NodeConnectorPropertiesWrapper getOFNode(String nodeId){
		NodeConnectorPropertiesWrapper ncp;
		try {
			WebResource res = TempData.client.resource(Constants.odlGetOFNode+nodeId);
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			for(NewCookie nc : TempData.odlCookies)
				builder.cookie(nc);
			ncp = builder.get(NodeConnectorPropertiesWrapper.class);
		}
		catch (RuntimeException e){
			TempData.logger.error(e.getMessage());
			//TempData.logger.error(e.getCause());
			//TempData.logger.error(e.getStackTrace());
			return null;
		}
		//TempData.logger.info(Utils.parseJsonToString(ncp));
		return ncp;
	}




	public static GetNetworkNodePortResponse getNodePortProperties(String nodeId, String portId) {
		Map<String, String> properties = new HashMap<String, String>();
		Map<String, String> links = new HashMap<String, String>();
		String ncp;
		try {
			WebResource res = TempData.client.resource(Constants.odlGetOFNode+nodeId);
			Builder builder = res.type(MediaType.APPLICATION_JSON);
			for(NewCookie nc : TempData.odlCookies)
				builder.cookie(nc);
			ncp = builder.get(String.class);
		}
		catch (RuntimeException e){
			TempData.logger.error(e.getMessage());
			//TempData.logger.error(e.getCause());
			//TempData.logger.error(e.getStackTrace());
			return null;
		}
		final JSONObject obj = new JSONObject(ncp);
		//TempData.logger.info(obj.toString());
	    final JSONArray nodeConnectorProperties = obj.getJSONArray("nodeConnectorProperties");
	    for (int i = 0; i < nodeConnectorProperties.length(); i++){
	    	String port = nodeConnectorProperties.getJSONObject(i).getJSONObject("nodeconnector").get("id").toString();
	    	if(port.equals(portId)){
	    		properties.put("name", nodeConnectorProperties.getJSONObject(i).getJSONObject("properties").getJSONObject("name").get("value").toString());
	    		properties.put("state", nodeConnectorProperties.getJSONObject(i).getJSONObject("properties").getJSONObject("state").get("value").toString());
	    		properties.put("type", nodeConnectorProperties.getJSONObject(i).getJSONObject("nodeconnector").get("type").toString());
	    		if(properties.get("type").equals("OF"))
	    			properties.put("bandwidth", nodeConnectorProperties.getJSONObject(i).getJSONObject("properties").getJSONObject("bandwidth").get("value").toString());

	    		Topology topology = OdlServices.getNetworkTopology();
	        	if(topology!=null){
	        		for(EdgeProperties ep : topology.getEdgeProperties()){
	        			if(ep.getEdge().getHeadNodeConnector().getNodeConnectorNode().getId().equals(nodeId))
	        				if(ep.getEdge().getHeadNodeConnector().getNodeConnectorIDString().equals(portId)){
	        					links.put("node", ep.getEdge().getTailNodeConnector().getNodeConnectorNode().getId());
	        					links.put("port", ep.getEdge().getTailNodeConnector().getNodeConnectorIDString());
	        			}
	        		}
	        	}
	        	else
	        		return null;
	    		return new GetNetworkNodePortResponse(properties, links);
	    	}
	    }
		return null;
	}



	public static int modBandwidth(String pathId, long bandwidth) {
		int enqueuePort = Utils.getMatchingQueue(bandwidth+"");
		for(FlowConfig f : TempData.installedFlow.get(pathId)){
			List<String> acts = new ArrayList<String>();
			String outputPort = Utils.getOutputPortFromAction(f.getActions().get(0));
			acts.add("ENQUEUE="+outputPort+":"+enqueuePort);
			f.setActions(acts);
			//TOBE fixes, due to hydrogen bug
			pushFlowWithPut(Utils.parseJsonToString(f), f.getNode().getId(), f.getName());
			pushFlowWithPost(Utils.parseJsonToString(f), f.getNode().getId(), f.getName());
			pushFlowWithPost(Utils.parseJsonToString(f), f.getNode().getId(), f.getName());
		}
		for(Path p : TempData.installedPaths)
			if(p.getPathName().equals(pathId))
				p.getFlow().setBandwidth(TempData.queueConfiguration.get(enqueuePort)+"");
		//TempData.installedPaths.get(index)
		return 0;
	}





}