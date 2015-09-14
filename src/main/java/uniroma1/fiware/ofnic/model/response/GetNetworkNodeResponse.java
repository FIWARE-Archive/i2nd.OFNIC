package uniroma1.fiware.ofnic.model.response;

import java.util.List;

import uniroma1.fiware.ofnic.model.Port;

public class GetNetworkNodeResponse {

	List<Port> ports;

	public GetNetworkNodeResponse(){

	}

	public GetNetworkNodeResponse(List<Port> ports) {
		super();
		this.ports = ports;
	}



	public List<Port> getPorts() {
		return ports;
	}

	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ports == null) ? 0 : ports.hashCode());
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
		GetNetworkNodeResponse other = (GetNetworkNodeResponse) obj;
		if (ports == null) {
			if (other.ports != null)
				return false;
		} else if (!ports.equals(other.ports))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GetNetworkNodeResponse [ports="
				+ ports + "]";
	}

}
