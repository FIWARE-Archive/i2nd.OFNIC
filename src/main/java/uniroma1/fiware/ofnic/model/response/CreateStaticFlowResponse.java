package uniroma1.fiware.ofnic.model.response;

public class CreateStaticFlowResponse {

    private String staticFlowName = "";

    public CreateStaticFlowResponse(){
    }

	public CreateStaticFlowResponse(
			String staticFlowName) {
		super();
		this.staticFlowName = staticFlowName;
	}



	public String getStaticFlowName() {
		return staticFlowName;
	}

	public void setStaticFlowName(String staticFlowName) {
		this.staticFlowName = staticFlowName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((staticFlowName == null) ? 0 : staticFlowName.hashCode());
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
		CreateStaticFlowResponse other = (CreateStaticFlowResponse) obj;
		if (staticFlowName == null) {
			if (other.staticFlowName != null)
				return false;
		} else if (!staticFlowName.equals(other.staticFlowName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreateStaticFlowResponse [staticFlowName="
				+ staticFlowName + "]";
	}

}
