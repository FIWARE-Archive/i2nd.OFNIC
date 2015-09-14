package uniroma1.fiware.ofnic.model;

public class Host {

	String macAddress, ipAddress, nodeId, nodePortId;

	public Host() {
		super();
	}

	public Host(String macAddress, String ipAddress, String nodeId,
			String nodePortId) {
		super();
		this.macAddress = macAddress;
		this.ipAddress = ipAddress;
		this.nodeId = nodeId;
		this.nodePortId = nodePortId;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodePortId() {
		return nodePortId;
	}

	public void setNodePortId(String nodePortId) {
		this.nodePortId = nodePortId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result
				+ ((macAddress == null) ? 0 : macAddress.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result
				+ ((nodePortId == null) ? 0 : nodePortId.hashCode());
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
		Host other = (Host) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (macAddress == null) {
			if (other.macAddress != null)
				return false;
		} else if (!macAddress.equals(other.macAddress))
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		if (nodePortId == null) {
			if (other.nodePortId != null)
				return false;
		} else if (!nodePortId.equals(other.nodePortId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Host [macAddress=" + macAddress + ", ipAddress=" + ipAddress
				+ ", nodeId=" + nodeId + ", nodePortId=" + nodePortId + "]";
	}


}
