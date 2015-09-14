package uniroma1.fiware.ofnic.model;

public class StaticFlowConfiguration {

	private String name = "";
	private String dp = "";
	private String nw_src = "";	//IPv4 addresses
	private String nw_dst = "";
	private String dl_src = ""; //SRC_DST MAC address
	private String dl_dst = "";
	private String tp_src = ""; //L4 TCP-UDP port
	private String tp_dst = "";
	private String bandwidth = "";
	private String input_port = "";
	private String output_port = "";
	private String etherType = "0x800";
	private String tosBits = "";
	private String priority = "";
	private String protocol = "";

	public StaticFlowConfiguration(){
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDp() {
		return dp;
	}

	public void setDp(String dp) {
		this.dp = dp;
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

	public String getInput_port() {
		return input_port;
	}

	public void setInput_port(String input_port) {
		this.input_port = input_port;
	}

	public String getOutput_port() {
		return output_port;
	}

	public void setOutput_port(String output_port) {
		this.output_port = output_port;
	}

	public String getEtherType() {
		return etherType;
	}

	public void setEtherType(String etherType) {
		this.etherType = etherType;
	}

	public String getTosBits() {
		return tosBits;
	}

	public void setTosBits(String tosBits) {
		this.tosBits = tosBits;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
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
		result = prime * result + ((dl_dst == null) ? 0 : dl_dst.hashCode());
		result = prime * result + ((dl_src == null) ? 0 : dl_src.hashCode());
		result = prime * result + ((dp == null) ? 0 : dp.hashCode());
		result = prime * result
				+ ((etherType == null) ? 0 : etherType.hashCode());
		result = prime * result
				+ ((input_port == null) ? 0 : input_port.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nw_dst == null) ? 0 : nw_dst.hashCode());
		result = prime * result + ((nw_src == null) ? 0 : nw_src.hashCode());
		result = prime * result
				+ ((output_port == null) ? 0 : output_port.hashCode());
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
		result = prime * result
				+ ((protocol == null) ? 0 : protocol.hashCode());
		result = prime * result + ((tosBits == null) ? 0 : tosBits.hashCode());
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
		StaticFlowConfiguration other = (StaticFlowConfiguration) obj;
		if (bandwidth == null) {
			if (other.bandwidth != null)
				return false;
		} else if (!bandwidth.equals(other.bandwidth))
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
		if (dp == null) {
			if (other.dp != null)
				return false;
		} else if (!dp.equals(other.dp))
			return false;
		if (etherType == null) {
			if (other.etherType != null)
				return false;
		} else if (!etherType.equals(other.etherType))
			return false;
		if (input_port == null) {
			if (other.input_port != null)
				return false;
		} else if (!input_port.equals(other.input_port))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		if (output_port == null) {
			if (other.output_port != null)
				return false;
		} else if (!output_port.equals(other.output_port))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (protocol == null) {
			if (other.protocol != null)
				return false;
		} else if (!protocol.equals(other.protocol))
			return false;
		if (tosBits == null) {
			if (other.tosBits != null)
				return false;
		} else if (!tosBits.equals(other.tosBits))
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
		return "StaticFlowConfiguration [name=" + name + ", dp=" + dp
				+ ", nw_src=" + nw_src + ", nw_dst=" + nw_dst + ", dl_src="
				+ dl_src + ", dl_dst=" + dl_dst + ", tp_src=" + tp_src
				+ ", tp_dst=" + tp_dst + ", bandwidth=" + bandwidth
				+ ", input_port=" + input_port + ", output_port=" + output_port
				+ ", etherType=" + etherType + ", tosBits=" + tosBits
				+ ", priority=" + priority + ", protocol=" + protocol + "]";
	}

	public StaticFlowConfiguration(String name, String dp, String nw_src,
			String nw_dst, String dl_src, String dl_dst, String tp_src,
			String tp_dst, String bandwidth, String input_port,
			String output_port, String etherType, String tosBits,
			String priority, String protocol) {
		super();
		this.name = name;
		this.dp = dp;
		this.nw_src = nw_src;
		this.nw_dst = nw_dst;
		this.dl_src = dl_src;
		this.dl_dst = dl_dst;
		this.tp_src = tp_src;
		this.tp_dst = tp_dst;
		this.bandwidth = bandwidth;
		this.input_port = input_port;
		this.output_port = output_port;
		this.etherType = etherType;
		this.tosBits = tosBits;
		this.priority = priority;
		this.protocol = protocol;
	}

}
