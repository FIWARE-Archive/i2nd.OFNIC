package uniroma1.fiware.ofnic.model.response;

public class CreateDirectPathResponse {

    private String pathName = "";

    public CreateDirectPathResponse(){
    }

	public CreateDirectPathResponse(String pathName) {
		super();
		this.pathName = pathName;
	}



	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}



}
