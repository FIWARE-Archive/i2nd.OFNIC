package uniroma1.fiware.ofnic.model.response;

import java.util.ArrayList;
import java.util.List;

public class GetAllVirtualPathResponse {

	Result result;

	public GetAllVirtualPathResponse(){
	}


	public GetAllVirtualPathResponse(List<String> pathsName){
		this.result = new Result();
		this.result.paths.addAll(pathsName);
	}





	public Result getResult() {
		return result;
	}


	public void setResult(Result result) {
		this.result = result;
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
		GetAllVirtualPathResponse other = (GetAllVirtualPathResponse) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "GetAllVirtualPathResponse [result=" + result + "]";
	}



}

class Result{
	List<String> paths = new ArrayList<String>();

	public Result(){
	}

	public Result(List<String> paths) {
		super();
		this.paths = paths;
	}

	public List<String> getPaths() {
		return paths;
	}

	public void setPaths(List<String> paths) {
		this.paths = paths;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paths == null) ? 0 : paths.hashCode());
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
		if (paths == null) {
			if (other.paths != null)
				return false;
		} else if (!paths.equals(other.paths))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Result [paths=" + paths + "]";
	}

}