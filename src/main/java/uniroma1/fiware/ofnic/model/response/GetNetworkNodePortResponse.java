package uniroma1.fiware.ofnic.model.response;

import java.util.Map;

public class GetNetworkNodePortResponse {

	Map<String, String> properties;
	Map<String, String> link;

	public GetNetworkNodePortResponse(){

	}

	public GetNetworkNodePortResponse(Map<String, String> properties, Map<String, String> link) {
		super();
		this.properties = properties;
		this.link = link;
	}


	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public Map<String, String> getLink() {
		return link;
	}

	public void setLink(Map<String, String> link) {
		this.link = link;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
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
		GetNetworkNodePortResponse other = (GetNetworkNodePortResponse) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GetNetworkNodePortResponse [properties=" + properties + ", link=" + link + "]";
	}

}
