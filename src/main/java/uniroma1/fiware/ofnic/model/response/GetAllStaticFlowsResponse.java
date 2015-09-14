package uniroma1.fiware.ofnic.model.response;

import java.util.ArrayList;
import java.util.List;

public class GetAllStaticFlowsResponse {

	Result result;

	public GetAllStaticFlowsResponse() {
	}

	public GetAllStaticFlowsResponse(List<String>  staticFlowName) {
		super();
		this.result = new Result();
		this.result.staticFlows.addAll(staticFlowName);
	}


	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "GetAllStaticFlowsResponse [result=" + result + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
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
		GetAllStaticFlowsResponse other = (GetAllStaticFlowsResponse) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}

	class Result {
		List<String> staticFlows = new ArrayList<String>();

		public Result() {
		}

		public List<String> getStaticFlows() {
			return staticFlows;
		}

		public void setStaticFlows(List<String> staticFlows) {
			this.staticFlows = staticFlows;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((staticFlows == null) ? 0 : staticFlows.hashCode());
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
			Result other = (Result) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (staticFlows == null) {
				if (other.staticFlows != null)
					return false;
			} else if (!staticFlows.equals(other.staticFlows))
				return false;
			return true;
		}

		private GetAllStaticFlowsResponse getOuterType() {
			return GetAllStaticFlowsResponse.this;
		}

		@Override
		public String toString() {
			return "Result [staticFlows=" + staticFlows + "]";
		}


	}
}
