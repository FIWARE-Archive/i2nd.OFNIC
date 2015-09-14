package uniroma1.fiware.ofnic.model.response;

import org.opendaylight.controller.sal.reader.FlowOnNode;

public class PathStatisticsResponse {

	String packetCount = "";
	String durationSeconds = "";
	String byteCount = "";
	String durationNanoseconds = "";


	public PathStatisticsResponse(){
	}


	public PathStatisticsResponse(FlowOnNode f){
		this.packetCount = f.getPacketCount()+"";
		this.durationSeconds = f.getDurationSeconds()+"";
		this.durationNanoseconds = f.getDurationNanoseconds()+"";
		this.byteCount = f.getByteCount()+"";
	}


	public PathStatisticsResponse(String packetCount, String durationSeconds,
			String byteCount, String durationNanoseconds, String tableId) {
		super();
		this.packetCount = packetCount;
		this.durationSeconds = durationSeconds;
		this.byteCount = byteCount;
		this.durationNanoseconds = durationNanoseconds;
	}


	public String getPacketCount() {
		return packetCount;
	}


	public void setPacketCount(String packetCount) {
		this.packetCount = packetCount;
	}


	public String getDurationSeconds() {
		return durationSeconds;
	}


	public void setDurationSeconds(String durationSeconds) {
		this.durationSeconds = durationSeconds;
	}


	public String getByteCount() {
		return byteCount;
	}


	public void setByteCount(String byteCount) {
		this.byteCount = byteCount;
	}


	public String getDurationNanoseconds() {
		return durationNanoseconds;
	}


	public void setDurationNanoseconds(String durationNanoseconds) {
		this.durationNanoseconds = durationNanoseconds;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((byteCount == null) ? 0 : byteCount.hashCode());
		result = prime
				* result
				+ ((durationNanoseconds == null) ? 0 : durationNanoseconds
						.hashCode());
		result = prime * result
				+ ((durationSeconds == null) ? 0 : durationSeconds.hashCode());
		result = prime * result
				+ ((packetCount == null) ? 0 : packetCount.hashCode());
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
		PathStatisticsResponse other = (PathStatisticsResponse) obj;
		if (byteCount == null) {
			if (other.byteCount != null)
				return false;
		} else if (!byteCount.equals(other.byteCount))
			return false;
		if (durationNanoseconds == null) {
			if (other.durationNanoseconds != null)
				return false;
		} else if (!durationNanoseconds.equals(other.durationNanoseconds))
			return false;
		if (durationSeconds == null) {
			if (other.durationSeconds != null)
				return false;
		} else if (!durationSeconds.equals(other.durationSeconds))
			return false;
		if (packetCount == null) {
			if (other.packetCount != null)
				return false;
		} else if (!packetCount.equals(other.packetCount))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "PathStatisticsResponse [packetCount=" + packetCount
				+ ", durationSeconds=" + durationSeconds + ", byteCount="
				+ byteCount + ", durationNanoseconds=" + durationNanoseconds
				+ "]";
	}

}
