package uniroma1.fiware.ofnic.controllers;

import java.util.ArrayList;

import org.opendaylight.controller.forwardingrulesmanager.FlowConfig;
import org.opendaylight.controller.sal.match.MatchField;
import org.opendaylight.controller.sal.reader.FlowOnNode;
import org.opendaylight.controller.sal.reader.NodeConnectorStatistics;
import org.opendaylight.controller.statistics.northbound.FlowStatistics;
import org.opendaylight.controller.statistics.northbound.PortStatistics;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uniroma1.fiware.ofnic.data.TempData;
import uniroma1.fiware.ofnic.exceptions.BadRequestException;
import uniroma1.fiware.ofnic.model.response.PathStatisticsResponse;
import uniroma1.fiware.ofnic.model.response.PortStatisticsResponse;
import uniroma1.fiware.ofnic.services.OdlServices;
import uniroma1.fiware.ofnic.utils.Utils;


@RequestMapping("/netic.v1/OFNIC/statistics")
@RestController
public class MonitoringController {



    @RequestMapping("/node/{nodeId}/port/{portId}")
    public String getPortStatistics(@PathVariable String nodeId, @PathVariable String portId) {
    	PortStatistics pStatistics = OdlServices.getAllNodePortStatistics(nodeId);
    	if(pStatistics==null)
    		throw new BadRequestException("Node id not found");
    	else{
    		for(NodeConnectorStatistics port : pStatistics.getPortStatistic()){
    			if(port.getNodeConnector().getNodeConnectorIDString().equals(portId))
    				return Utils.parseJsonToString(new PortStatisticsResponse(port));
    		}
    	}
    	throw new BadRequestException("Port id not found on node");
    }


	@RequestMapping("/virtualpath/{pathId}")
	public String getPathStatistics(@PathVariable String pathId) {
		ArrayList<FlowConfig> flowList = TempData.installedFlow.get(pathId);

		if (flowList == null)
			throw new BadRequestException("Error parsing the request");
		if (flowList.size() == 0)
			throw new BadRequestException("Error parsing the request");
		FlowConfig fc = flowList.get(0);

		FlowStatistics fStatistics = OdlServices.getAllNodeFlowStatistics(fc.getNode().getId());
		for (FlowOnNode fon : fStatistics.getFlowStatistic()) {
			boolean match = true;
			if (fc.getActions().size() != fon.getFlow().getActions().size())
				match = false;
			if (fc.getActions().size() > 0 && fon.getFlow().getActions().size() > 0){
				if (!fc.getActions().get(0).equals(fon.getFlow().getActions().get(0).toString()))
					match = false;
				}

			if (!fc.getPriority().toString().equals(fon.getFlow().getPriority()+""))
				match = false;
			for (MatchField mf : fon.getFlow().getMatch().getMatchFields()) {
				switch (mf.getType()) {
					case "DL_TYPE":
						int ethType = Integer.decode(fc.getEtherType());
						if (!(ethType+"").equals(mf.getValue().toString()))
							match = false;
						break;
					case "NW_SRC":
						if (!fc.getNwSrc().equals(mf.getValue()))
							match = false;
						break;
					case "NW_DST":
						if (!fc.getNwDst().equals(mf.getValue()))
							match = false;
						break;
					case "DL_SRC":
						if (!fc.getDlSrc().equals(mf.getValue()))
							match = false;
						break;
					case "DL_DST":
						if (!fc.getDlDst().equals(mf.getValue()))
							match = false;
						break;
					case "TP_SRC":
						if (!fc.getTpSrc().equals(mf.getValue()))
							match = false;
						break;
					case "TP_DST":
						if (!fc.getTpDst().equals(mf.getValue()))
							match = false;
						break;
					case "IN_PORT":
						String port = mf.getValue().toString().split("@")[0];
						port = port.substring(3, port.length());
						if (!port.equals(fc.getIngressPort()))
							match = false;
						break;
					}
			}
			if(match){
				return Utils.parseJsonToString(new PathStatisticsResponse(fon));
			}
		}
		throw new BadRequestException("Requested path not found");
	}

}