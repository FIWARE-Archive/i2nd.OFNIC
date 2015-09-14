package uniroma1.fiware.ofnic.model;

public class FlowConfiguration {

	private String nw_src = "";	//IPv4 addresses
	private String nw_dst = "";
	private String dp_src = ""; //FIRST_LAST nodeID (OF)
	private String dp_dst = "";
	private String dl_src = ""; //SRC_DST MAC address
	private String dl_dst = "";
	private String tp_src = ""; //L4 TCP-UDP port
	private String tp_dst = "";
	private String bandwidth = "";
	private String bidirectional = "";
	private String first_port = "";
	private String last_port = "";
	//private String etherType = "0x800";
	private String protocol = "";


	public FlowConfiguration(){
	}


	public FlowConfiguration(String nw_src, String nw_dst, String dp_src,
			String dp_dst, String dl_src, String dl_dst, String tp_src,
			String tp_dst, String bandwidth, String bidirectional,
			String first_port, String last_port, String protocol) {
		super();
		this.nw_src = nw_src;
		this.nw_dst = nw_dst;
		this.dp_src = dp_src;
		this.dp_dst = dp_dst;
		this.dl_src = dl_src;
		this.dl_dst = dl_dst;
		this.tp_src = tp_src;
		this.tp_dst = tp_dst;
		this.bandwidth = bandwidth;
		this.bidirectional = bidirectional;
		this.first_port = first_port;
		this.last_port = last_port;
		this.protocol = protocol;
	}


	public String getNw_src() {
		return nw_src;
	}


	public void setNw_src(String nw_src) {
		this.nw_src = nw_src;
	}


	public String getNw_dst() {
		return nw_dst;
	}


	public void setNw_dst(String nw_dst) {
		this.nw_dst = nw_dst;
	}


	public String getDp_src() {
		return dp_src;
	}


	public void setDp_src(String dp_src) {
		this.dp_src = dp_src;
	}


	public String getDp_dst() {
		return dp_dst;
	}


	public void setDp_dst(String dp_dst) {
		this.dp_dst = dp_dst;
	}


	public String getDl_src() {
		return dl_src;
	}


	public void setDl_src(String dl_src) {
		this.dl_src = dl_src;
	}


	public String getDl_dst() {
		return dl_dst;
	}


	public void setDl_dst(String dl_dst) {
		this.dl_dst = dl_dst;
	}


	public String getTp_src() {
		return tp_src;
	}


	public void setTp_src(String tp_src) {
		this.tp_src = tp_src;
	}


	public String getTp_dst() {
		return tp_dst;
	}


	public void setTp_dst(String tp_dst) {
		this.tp_dst = tp_dst;
	}


	public String getBandwidth() {
		return bandwidth;
	}


	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}


	public String getBidirectional() {
		return bidirectional;
	}


	public void setBidirectional(String bidirectional) {
		this.bidirectional = bidirectional;
	}


	public String getFirst_port() {
		return first_port;
	}


	public void setFirst_port(String first_port) {
		this.first_port = first_port;
	}


	public String getLast_port() {
		return last_port;
	}


	public void setLast_port(String last_port) {
		this.last_port = last_port;
	}


	public String getProtocol() {
		return protocol;
	}


	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bandwidth == null) ? 0 : bandwidth.hashCode());
		result = prime * result
				+ ((bidirectional == null) ? 0 : bidirectional.hashCode());
		result = prime * result + ((dl_dst == null) ? 0 : dl_dst.hashCode());
		result = prime * result + ((dl_src == null) ? 0 : dl_src.hashCode());
		result = prime * result + ((dp_dst == null) ? 0 : dp_dst.hashCode());
		result = prime * result + ((dp_src == null) ? 0 : dp_src.hashCode());
		result = prime * result
				+ ((first_port == null) ? 0 : first_port.hashCode());
		result = prime * result
				+ ((last_port == null) ? 0 : last_port.hashCode());
		result = prime * result + ((nw_dst == null) ? 0 : nw_dst.hashCode());
		result = prime * result + ((nw_src == null) ? 0 : nw_src.hashCode());
		result = prime * result
				+ ((protocol == null) ? 0 : protocol.hashCode());
		result = prime * result + ((tp_dst == null) ? 0 : tp_dst.hashCode());
		result = prime * result + ((tp_src == null) ? 0 : tp_src.hashCode());
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
		FlowConfiguration other = (FlowConfiguration) obj;
		if (bandwidth == null) {
			if (other.bandwidth != null)
				return false;
		} else if (!bandwidth.equals(other.bandwidth))
			return false;
		if (bidirectional == null) {
			if (other.bidirectional != null)
				return false;
		} else if (!bidirectional.equals(other.bidirectional))
			return false;
		if (dl_dst == null) {
			if (other.dl_dst != null)
				return false;
		} else if (!dl_dst.equals(other.dl_dst))
			return false;
		if (dl_src == null) {
			if (other.dl_src != null)
				return false;
		} else if (!dl_src.equals(other.dl_src))
			return false;
		if (dp_dst == null) {
			if (other.dp_dst != null)
				return false;
		} else if (!dp_dst.equals(other.dp_dst))
			return false;
		if (dp_src == null) {
			if (other.dp_src != null)
				return false;
		} else if (!dp_src.equals(other.dp_src))
			return false;
		if (first_port == null) {
			if (other.first_port != null)
				return false;
		} else if (!first_port.equals(other.first_port))
			return false;
		if (last_port == null) {
			if (other.last_port != null)
				return false;
		} else if (!last_port.equals(other.last_port))
			return false;
		if (nw_dst == null) {
			if (other.nw_dst != null)
				return false;
		} else if (!nw_dst.equals(other.nw_dst))
			return false;
		if (nw_src == null) {
			if (other.nw_src != null)
				return false;
		} else if (!nw_src.equals(other.nw_src))
			return false;
		if (protocol == null) {
			if (other.protocol != null)
				return false;
		} else if (!protocol.equals(other.protocol))
			return false;
		if (tp_dst == null) {
			if (other.tp_dst != null)
				return false;
		} else if (!tp_dst.equals(other.tp_dst))
			return false;
		if (tp_src == null) {
			if (other.tp_src != null)
				return false;
		} else if (!tp_src.equals(other.tp_src))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "FlowConfiguration [nw_src=" + nw_src + ", nw_dst=" + nw_dst
				+ ", dp_src=" + dp_src + ", dp_dst=" + dp_dst + ", dl_src="
				+ dl_src + ", dl_dst=" + dl_dst + ", tp_src=" + tp_src
				+ ", tp_dst=" + tp_dst + ", bandwidth=" + bandwidth
				+ ", bidirectional=" + bidirectional + ", first_port="
				+ first_port + ", last_port=" + last_port + ", protocol=" + protocol + "]";
	}



}
