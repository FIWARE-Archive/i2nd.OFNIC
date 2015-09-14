package uniroma1.fiware.ofnic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opendaylight.controller.connectionmanager.northbound.Nodes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uniroma1.fiware.ofnic.data.Constants;
import uniroma1.fiware.ofnic.data.TempData;
import uniroma1.fiware.ofnic.exceptions.BadRequestException;
import uniroma1.fiware.ofnic.model.StaticFlowConfiguration;
import uniroma1.fiware.ofnic.model.response.CreateStaticFlowResponse;
import uniroma1.fiware.ofnic.model.response.GetAllStaticFlowsResponse;
import uniroma1.fiware.ofnic.services.OdlServices;
import uniroma1.fiware.ofnic.utils.Utils;



@RequestMapping("/netic.v1/OFNIC")
@RestController
public class StaticFlowController {

	static final Logger logger = Logger.getLogger(SynchronizeController.class);


	@RequestMapping(value = "/staticflow/{nodeId}/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String installStaticFlow(@RequestBody StaticFlowConfiguration staticFlowConfiguration, @PathVariable String nodeId) {
		Nodes ns = OdlServices.getAllNodes();
		if(ns==null)
			throw new BadRequestException("Gel all nodes controller error");
		if(staticFlowConfiguration.getInput_port().equals(""))
			throw new BadRequestException("Flow configuration error, input port is mandatory");
		if(staticFlowConfiguration.getOutput_port().equals(""))
			throw new BadRequestException("Flow configuration error, output port is mandatory");
   		if(staticFlowConfiguration.getPriority().equals(""))
   			staticFlowConfiguration.setPriority(Constants.BASE_FLOW_PRIORITY.toString());
    	if(staticFlowConfiguration.getEtherType().equals(""))
    		staticFlowConfiguration.setEtherType("0x800");
    	int r = OdlServices.installStaticFlow(nodeId, staticFlowConfiguration);
		if(r<0)
			throw new BadRequestException("Error when pushing flow configuration");
		return Utils.parseJsonToString(new CreateStaticFlowResponse(
				TempData.installedStaticFlow.get(TempData.installedStaticFlow.size()-1).getName()));
    }



    @RequestMapping(value = "/staticflow/{id}", method = RequestMethod.DELETE)
    public String deleteStaticFlow(@PathVariable String id) {
    	for(StaticFlowConfiguration f : TempData.installedStaticFlow){
    		if(id.equals(f.getName())){
    			OdlServices.removeStaticFlows(id);
    			return "Static flow deleted";
    		}
    	}
    	throw new BadRequestException("Error, static flow id not found");
    }



    @RequestMapping(value = "/staticflow/{id}", method = RequestMethod.GET)
    public String getStaticFlow(@PathVariable String id) {
    	for(StaticFlowConfiguration f : TempData.installedStaticFlow)
    		if(id.equals(f.getName()))
    			return Utils.parseJsonToString(f);
    	throw new BadRequestException("Error, static flow id not found");
    }



    @RequestMapping(value = "/staticflow", method = RequestMethod.GET)
    public String getAllStaticFlow() {
    	List<String> staticFlowsName = new ArrayList<String>();
    	for(StaticFlowConfiguration f : TempData.installedStaticFlow)
    		staticFlowsName.add(f.getName());
    	return Utils.parseJsonToString(new GetAllStaticFlowsResponse(staticFlowsName));
    }

}