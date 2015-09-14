package uniroma1.fiware.ofnic.services;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.controller.hosttracker.northbound.HostConfig;
import org.opendaylight.controller.hosttracker.northbound.Hosts;
import org.opendaylight.controller.sal.core.Edge;
import org.opendaylight.controller.topology.northbound.EdgeProperties;
import org.opendaylight.controller.topology.northbound.Topology;

import uniroma1.fiware.ofnic.data.TempData;
import uniroma1.fiware.ofnic.model.Link;
import uniroma1.fiware.ofnic.model.Path;

public class OdlUtilServices {

	private static ArrayList<Path> allPaths = new ArrayList<Path>();
	private static Topology topology;


	private static ArrayList<Edge> getOutEdgesFromNode(String nodeID, Topology topology){
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for(EdgeProperties ep : topology.getEdgeProperties()){
			if(ep.getEdge().getHeadNodeConnector().getNodeConnectorNode().getId().equals(nodeID) &&
					ep.getEdge().getTailNodeConnector().getType().equals("OF")){
				edges.add(ep.getEdge());
			}
		}
		return edges;
	}


	public static ArrayList<Path> findPaths(HostConfig srcHostConfig, HostConfig dstHostConfig){
		topology = OdlServices.getNetworkTopology();
		allPaths = new ArrayList<Path>();
		Path path = new Path(new ArrayList<Link>(0));
		List<String> visited = new ArrayList<String>(0);
		visited.add(srcHostConfig.getNodeId());
		allPath(visited, dstHostConfig.getNodeId(), path);
		return allPaths;
	}


	private static void allPath(List<String> visited, String dstNodeID, Path path){
		String lastNodeVisited = visited.get(visited.size()-1);
		for(Edge e : getOutEdgesFromNode(lastNodeVisited, topology)){
			String node = e.getTailNodeConnector().getNodeConnectorNode().getId();
			if(visited.contains(node)){
				continue;
			}
			visited.add(node);
			Link l = new Link(lastNodeVisited, node, e.getHeadNodeConnector().getNodeConnectorIDString(), e.getTailNodeConnector().getNodeConnectorIDString());
			path.addEdgeToPath(l);
			if(node.equals(dstNodeID)){
				allPaths.add(path);
				break;
			}
			allPath(visited, dstNodeID, path);
		}
	}


	public static void printPath(HostConfig srcHostConfig, HostConfig dstHostConfig, ArrayList<Path> path) {
		TempData.logger.info("Path:");
		TempData.logger.info(srcHostConfig.getNodeId()+"("+srcHostConfig.getNodeConnectorId()+")");
		for(Link l : path.get(0).getEdgeList()){
			TempData.logger.info(l.headNode+"("+l.headPort+")");
			TempData.logger.info(l.tailNode+"("+l.tailPort+")");
		}
		TempData.logger.info(dstHostConfig.getNodeId()+"("+dstHostConfig.getNodeConnectorId()+")");
	}


	public static List<String> getNodeFromTopology(Topology topology) {
		List<String> nodes = new ArrayList<String>();
		for(EdgeProperties ep : topology.getEdgeProperties()){
			if(ep.getEdge()!=null){
				if(!nodes.contains(ep.getEdge().getHeadNodeConnector().getNodeConnectorNode().getId().toString()))
						nodes.add(ep.getEdge().getHeadNodeConnector().getNodeConnectorNode().getId().toString());
				if(!nodes.contains(ep.getEdge().getTailNodeConnector().getNodeConnectorNode().getId().toString()))
					nodes.add(ep.getEdge().getTailNodeConnector().getNodeConnectorNode().getId().toString());
				}
		}
		return nodes;
	}

	public static List<String> getHostsList(){
		List<String> listHosts = new ArrayList<String>();
		Hosts hosts = OdlServices.getAllHosts();
		if(hosts!=null){
			for(HostConfig h : hosts.getHostConfig())
				listHosts.add(h.getNetworkAddress());
		}
		return listHosts;
	}
}
