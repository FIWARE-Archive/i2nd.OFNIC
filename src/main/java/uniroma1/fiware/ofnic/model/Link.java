package uniroma1.fiware.ofnic.model;


public class Link{

	public String headNode = "", tailNode = "", headPort = "", tailPort = "";

	public Link(){

	}

	public Link(String headNode, String tailNode, String headPort, String tailPort){
		this.headNode = headNode;
		this.tailNode = tailNode;
		this.headPort = headPort;
		this.tailPort = tailPort;
	}

	public String getHeadNode() {
		return headNode;
	}

	public void setHeadNode(String headNode) {
		this.headNode = headNode;
	}

	public String getTailNode() {
		return tailNode;
	}

	public void setTailNode(String tailNode) {
		this.tailNode = tailNode;
	}

	public String getHeadPort() {
		return headPort;
	}

	public void setHeadPort(String headPort) {
		this.headPort = headPort;
	}

	public String getTailPort() {
		return tailPort;
	}

	public void setTailPort(String tailPort) {
		this.tailPort = tailPort;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((headNode == null) ? 0 : headNode.hashCode());
		result = prime * result
				+ ((headPort == null) ? 0 : headPort.hashCode());
		result = prime * result
				+ ((tailNode == null) ? 0 : tailNode.hashCode());
		result = prime * result
				+ ((tailPort == null) ? 0 : tailPort.hashCode());
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
		Link other = (Link) obj;
		if (headNode == null) {
			if (other.headNode != null)
				return false;
		} else if (!headNode.equals(other.headNode))
			return false;
		if (headPort == null) {
			if (other.headPort != null)
				return false;
		} else if (!headPort.equals(other.headPort))
			return false;
		if (tailNode == null) {
			if (other.tailNode != null)
				return false;
		} else if (!tailNode.equals(other.tailNode))
			return false;
		if (tailPort == null) {
			if (other.tailPort != null)
				return false;
		} else if (!tailPort.equals(other.tailPort))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Link [headNode=" + headNode + ", tailNode=" + tailNode
				+ ", headPort=" + headPort + ", tailPort=" + tailPort + "]";
	}

}

