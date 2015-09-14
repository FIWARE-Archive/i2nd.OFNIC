package uniroma1.fiware.ofnic.model.response;

import java.util.List;

public class GetNetworkResponse{

	List<String> paths;
	List<String> nodes;
	List<String> hosts;

	public GetNetworkResponse(){
	}

	public GetNetworkResponse(List<String> paths, List<String> nodes, List<String> hosts) {
		super();
		this.paths = paths;
		this.nodes = nodes;
		this.hosts = hosts;
	}

	public List<String> getPaths() {
		return paths;
	}

	public void setPaths(List<String> paths) {
		this.paths = paths;
	}

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public List<String> getHosts() {
		return hosts;
	}

	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hosts == null) ? 0 : hosts.hashCode());
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		result = prime * result + ((paths == null) ? 0 : paths.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GetNetworkResponse other = (GetNetworkResponse) obj;
		if (hosts == null) {
			if (other.hosts != null)
				return false;
		} else if (!hosts.equals(other.hosts))
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		if (paths == null) {
			if (other.paths != null)
				return false;
		} else if (!paths.equals(other.paths))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GetNetworkResponse [paths=" + paths + ", nodes=" + nodes
				+ ", hosts=" + hosts + "]";
	}




}
