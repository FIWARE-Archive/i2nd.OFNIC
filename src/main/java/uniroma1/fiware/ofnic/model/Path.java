package uniroma1.fiware.ofnic.model;

import java.util.ArrayList;
import java.util.Random;

public class Path{

	private String pathName = "";
	private FlowConfiguration flow;
	private ArrayList<Link> edgeList = new ArrayList<Link>();

	public Path(){
	}



	public FlowConfiguration getFlow() {
		return flow;
	}



	public void setFlow(FlowConfiguration flow) {
		this.flow = flow;
	}


	public Path(ArrayList<Link> edgeList) {
		this.edgeList = edgeList;
		this.pathName = new Integer(new Random().nextInt(100000000)).toString();
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public void setEdgeList(ArrayList<Link> edgeList) {
		this.edgeList = edgeList;
	}

	public ArrayList<Link> getEdgeList(){
		return edgeList;
	}

	public void addEdgeToPath(Link e){
		edgeList.add(e);
	}
}
