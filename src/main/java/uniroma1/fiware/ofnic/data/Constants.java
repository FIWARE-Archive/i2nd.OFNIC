package uniroma1.fiware.ofnic.data;

public class Constants {


	private static final String ODL_ADDRESS = "http://127.0.0.1:8080";
	private static final String ODL_BASE_PATH = ODL_ADDRESS+"/controller/nb/v2";
	//public static final String OFNIC_BASE_PATH = "/netic.v1/OFNIC";
	public static Integer BASE_FLOW_PRIORITY = 200;

	public static final String ODL_username = "admin";
	public static final String ODL_password = "admin";

	public static final String odlGetTopologyWSPath = ODL_BASE_PATH + "/topology/default";
	public static final String odlGetAllNodesWSPath = ODL_BASE_PATH + "/connectionmanager/nodes";
	public static final String odlGetNetworkTopology = ODL_BASE_PATH + "/topology/default";
	public static final String odlRemoveFlow = ODL_BASE_PATH + "/flowprogrammer/default/node/OF/";
	public static final String odlAddFlow = ODL_BASE_PATH + "/flowprogrammer/default/node/OF";
	public static final String odlGetHostConfiguration = ODL_BASE_PATH + "/hosttracker/default/address";
	public static final String odlGetAllHostConfiguration = ODL_BASE_PATH + "/hosttracker/default/hosts/active";
	public static final String odlGetAllPortsStatistics = ODL_BASE_PATH + "/statistics/default/port/node/OF/";
	public static final String odlGelNodeFlowsStatistics = ODL_BASE_PATH + "/statistics/default/flow/node/OF/";
	public static final String odlGetOFNode = ODL_BASE_PATH + "/switchmanager/default/node/OF/";

}
