package uniroma1.fiware.ofnic.model.response;

import org.opendaylight.controller.sal.reader.NodeConnectorStatistics;

public class PortStatisticsResponse {
	String receiveFrameError = "";
	String receiveCrcError = "";
	String receiveDrops = "";
	String receiveBytes = "";
	String receiveErrors = "";
	String receivePackets = "";
	String receiveOverRunError = "";
	String transmitBytes = "";
	String transmitDrops = "";
	String transmitErrors = "";
	String transmitPackets = "";
	String collisionCount = "";

	public PortStatisticsResponse(){
	}


	public PortStatisticsResponse(NodeConnectorStatistics ps){
		this.collisionCount = ps.getCollisionCount()+"";
		this.receiveBytes = ps.getReceiveBytes()+"";
		this.receiveCrcError = ps.getReceiveCrcError()+"";
		this.receiveDrops = ps.getReceiveDrops()+"";
		this.receiveErrors = ps.getReceiveErrors()+"";
		this.receiveFrameError = ps.getReceiveFrameError()+"";
		this.receiveOverRunError = ps.getReceiveOverRunError()+"";
		this.receivePackets = ps.getReceivePackets()+"";
		this.transmitBytes = ps.getTransmitBytes()+"";
		this.transmitDrops = ps.getTransmitDrops()+"";
		this.transmitErrors = ps.getTransmitErrors()+"";
		this.transmitPackets = ps.getTransmitPackets()+"";
	}


	public PortStatisticsResponse(String receiveFrameError, String receiveCrcError,
			String receiveDrops, String receiveBytes, String receiveErrors,
			String receiveOverRunError, String transmitBytes,
			String transmitDrops, String transmitErrors,
			String transmitPackets, String collisionCount) {
		super();
		this.receiveFrameError = receiveFrameError;
		this.receiveCrcError = receiveCrcError;
		this.receiveDrops = receiveDrops;
		this.receiveBytes = receiveBytes;
		this.receiveErrors = receiveErrors;
		this.receiveOverRunError = receiveOverRunError;
		this.transmitBytes = transmitBytes;
		this.transmitDrops = transmitDrops;
		this.transmitErrors = transmitErrors;
		this.transmitPackets = transmitPackets;
		this.collisionCount = collisionCount;
	}


	public String getReceiveFrameError() {
		return receiveFrameError;
	}

	public void setReceiveFrameError(String receiveFrameError) {
		this.receiveFrameError = receiveFrameError;
	}

	public String getReceiveCrcError() {
		return receiveCrcError;
	}

	public void setReceiveCrcError(String receiveCrcError) {
		this.receiveCrcError = receiveCrcError;
	}

	public String getReceiveDrops() {
		return receiveDrops;
	}

	public void setReceiveDrops(String receiveDrops) {
		this.receiveDrops = receiveDrops;
	}

	public String getReceiveBytes() {
		return receiveBytes;
	}

	public void setReceiveBytes(String receiveBytes) {
		this.receiveBytes = receiveBytes;
	}

	public String getReceiveErrors() {
		return receiveErrors;
	}

	public void setReceiveErrors(String receiveErrors) {
		this.receiveErrors = receiveErrors;
	}

	public String getReceiveOverRunError() {
		return receiveOverRunError;
	}

	public void setReceiveOverRunError(String receiveOverRunError) {
		this.receiveOverRunError = receiveOverRunError;
	}

	public String getTransmitBytes() {
		return transmitBytes;
	}

	public void setTransmitBytes(String transmitBytes) {
		this.transmitBytes = transmitBytes;
	}

	public String getTransmitDrops() {
		return transmitDrops;
	}

	public void setTransmitDrops(String transmitDrops) {
		this.transmitDrops = transmitDrops;
	}

	public String getTransmitErrors() {
		return transmitErrors;
	}

	public void setTransmitErrors(String transmitErrors) {
		this.transmitErrors = transmitErrors;
	}

	public String getTransmitPackets() {
		return transmitPackets;
	}

	public void setTransmitPackets(String transmitPackets) {
		this.transmitPackets = transmitPackets;
	}

	public String getCollisionCount() {
		return collisionCount;
	}

	public void setCollisionCount(String collisionCount) {
		this.collisionCount = collisionCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collisionCount == null) ? 0 : collisionCount.hashCode());
		result = prime * result
				+ ((receiveBytes == null) ? 0 : receiveBytes.hashCode());
		result = prime * result
				+ ((receiveCrcError == null) ? 0 : receiveCrcError.hashCode());
		result = prime * result
				+ ((receiveDrops == null) ? 0 : receiveDrops.hashCode());
		result = prime * result
				+ ((receiveErrors == null) ? 0 : receiveErrors.hashCode());
		result = prime
				* result
				+ ((receiveFrameError == null) ? 0 : receiveFrameError
						.hashCode());
		result = prime
				* result
				+ ((receiveOverRunError == null) ? 0 : receiveOverRunError
						.hashCode());
		result = prime * result
				+ ((transmitBytes == null) ? 0 : transmitBytes.hashCode());
		result = prime * result
				+ ((transmitDrops == null) ? 0 : transmitDrops.hashCode());
		result = prime * result
				+ ((transmitErrors == null) ? 0 : transmitErrors.hashCode());
		result = prime * result
				+ ((transmitPackets == null) ? 0 : transmitPackets.hashCode());
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
		PortStatisticsResponse other = (PortStatisticsResponse) obj;
		if (collisionCount == null) {
			if (other.collisionCount != null)
				return false;
		} else if (!collisionCount.equals(other.collisionCount))
			return false;
		if (receiveBytes == null) {
			if (other.receiveBytes != null)
				return false;
		} else if (!receiveBytes.equals(other.receiveBytes))
			return false;
		if (receiveCrcError == null) {
			if (other.receiveCrcError != null)
				return false;
		} else if (!receiveCrcError.equals(other.receiveCrcError))
			return false;
		if (receiveDrops == null) {
			if (other.receiveDrops != null)
				return false;
		} else if (!receiveDrops.equals(other.receiveDrops))
			return false;
		if (receiveErrors == null) {
			if (other.receiveErrors != null)
				return false;
		} else if (!receiveErrors.equals(other.receiveErrors))
			return false;
		if (receiveFrameError == null) {
			if (other.receiveFrameError != null)
				return false;
		} else if (!receiveFrameError.equals(other.receiveFrameError))
			return false;
		if (receiveOverRunError == null) {
			if (other.receiveOverRunError != null)
				return false;
		} else if (!receiveOverRunError.equals(other.receiveOverRunError))
			return false;
		if (transmitBytes == null) {
			if (other.transmitBytes != null)
				return false;
		} else if (!transmitBytes.equals(other.transmitBytes))
			return false;
		if (transmitDrops == null) {
			if (other.transmitDrops != null)
				return false;
		} else if (!transmitDrops.equals(other.transmitDrops))
			return false;
		if (transmitErrors == null) {
			if (other.transmitErrors != null)
				return false;
		} else if (!transmitErrors.equals(other.transmitErrors))
			return false;
		if (transmitPackets == null) {
			if (other.transmitPackets != null)
				return false;
		} else if (!transmitPackets.equals(other.transmitPackets))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PortStatistics [receiveFrameError=" + receiveFrameError
				+ ", receiveCrcError=" + receiveCrcError + ", receiveDrops="
				+ receiveDrops + ", receiveBytes=" + receiveBytes
				+ ", receiveErrors=" + receiveErrors + ", receiveOverRunError="
				+ receiveOverRunError + ", transmitBytes=" + transmitBytes
				+ ", transmitDrops=" + transmitDrops + ", transmitErrors="
				+ transmitErrors + ", transmitPackets=" + transmitPackets
				+ ", collisionCount=" + collisionCount + "]";
	}



}
