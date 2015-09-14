package uniroma1.fiware.ofnic.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.ws.rs.core.NewCookie;

import org.apache.log4j.Logger;
import org.opendaylight.controller.forwardingrulesmanager.FlowConfig;
import org.opendaylight.controller.topology.northbound.Topology;

import com.sun.jersey.api.client.Client;

import uniroma1.fiware.ofnic.controllers.SynchronizeController;
import uniroma1.fiware.ofnic.model.Path;
import uniroma1.fiware.ofnic.model.StaticFlowConfiguration;

public class TempData {

	public static final Logger logger = Logger.getLogger(SynchronizeController.class);
	public static List<Path> installedPaths = new ArrayList<Path>();
	public static Client client;
	public static Map<String, ArrayList<FlowConfig>> installedFlow = new HashMap<String, ArrayList<FlowConfig>>();
	public static List<StaticFlowConfiguration> installedStaticFlow = new ArrayList<StaticFlowConfiguration>();
	public static List<Long> queueConfiguration;
	public static Topology lastKnownTopology = null;
	public static List<NewCookie> odlCookies = null;
}
