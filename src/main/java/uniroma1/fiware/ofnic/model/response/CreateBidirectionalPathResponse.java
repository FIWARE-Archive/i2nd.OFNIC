package uniroma1.fiware.ofnic.model.response;

public class CreateBidirectionalPathResponse {

    String directPathName = "";
	private String reversPathName = "";

    public CreateBidirectionalPathResponse(){
    }

	public CreateBidirectionalPathResponse(String directPathName, String reversPathName) {
		super();
		this.directPathName = directPathName;
		this.reversPathName = reversPathName;
	}


	public String getDirectPathName() {
		return directPathName;
	}

	public void setDirectPathName(String directPathName) {
		this.directPathName = directPathName;
	}

	public String getReversPathName() {
		return reversPathName;
	}

	public void setReversPathName(String reversPathName) {
		this.reversPathName = reversPathName;
	}



}
