package uniroma1.fiware.ofnic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.controller.hosttracker.northbound.HostConfig;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uniroma1.fiware.ofnic.data.TempData;
import uniroma1.fiware.ofnic.exceptions.BadRequestException;
import uniroma1.fiware.ofnic.model.FlowConfiguration;
import uniroma1.fiware.ofnic.model.Path;
import uniroma1.fiware.ofnic.model.response.CreateBidirectionalPathResponse;
import uniroma1.fiware.ofnic.model.response.CreateDirectPathResponse;
import uniroma1.fiware.ofnic.model.response.GetAllVirtualPathResponse;
import uniroma1.fiware.ofnic.services.OdlServices;
import uniroma1.fiware.ofnic.services.OdlUtilServices;
import uniroma1.fiware.ofnic.utils.Utils;


@RequestMapping("/netic.v1/OFNIC")
@RestController
public class VirtualPathController {



    @RequestMapping(value = "/virtualpath/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String installPathFlows(@RequestBody FlowConfiguration flowConfiguration) {

    	TempData.logger.info(flowConfiguration);

    	String directPath = "", reversePath = "";
    	HostConfig srcHostConfig = new HostConfig(), dstHostConfig = new HostConfig();
    		if(flowConfiguration.getFirst_port()==null || flowConfiguration.getFirst_port().equals("")){
    			if(!flowConfiguration.getNw_src().equals("")){
    				srcHostConfig = OdlServices.getHostConfiguration(flowConfiguration.getNw_src());
    				if(srcHostConfig==null)
    					throw new BadRequestException("Get Host configuration fails");
    				else{
    					flowConfiguration.setDp_src(srcHostConfig.getNodeId());
    					flowConfiguration.setFirst_port(srcHostConfig.getNodeConnectorId());
    				}
    			}
    			else
    				throw new BadRequestException("Flow configuration error on field NW_SRC");
    			if(!flowConfiguration.getNw_dst().equals("")){
    				dstHostConfig = OdlServices.getHostConfiguration(flowConfiguration.getNw_dst());
    				if(dstHostConfig==null)
    					throw new BadRequestException("Get Host configuration fails");
    				else{
    					flowConfiguration.setDp_dst(dstHostConfig.getNodeId());
    					flowConfiguration.setLast_port(dstHostConfig.getNodeConnectorId());
    				}
    			}
    			//throw new OfnicException("Flow configuration error on field NW_DST");
    		}
    		else{
    			if(!flowConfiguration.getDp_dst().equals(""))
    				srcHostConfig.setNodeId(flowConfiguration.getDp_src());
    			else
    				throw new BadRequestException("Flow configuration error on field DP_SRC");
    			if(!flowConfiguration.getNw_src().equals(""))
    				srcHostConfig.setNetworkAddress(flowConfiguration.getNw_src());
    			else
    				throw new BadRequestException("Flow configuration error on field NW_SRC");
        		srcHostConfig.setNodeType("OF");
        		if(!flowConfiguration.getFirst_port().equals(""))
        			srcHostConfig.setNodeConnectorId(flowConfiguration.getFirst_port());
    			else
    				throw new BadRequestException("Flow configuration error on field FIRST_PORT");

        		if(!flowConfiguration.getDp_dst().equals(""))
        			dstHostConfig.setNodeId(flowConfiguration.getDp_dst());
    			else
    				throw new BadRequestException("Flow configuration error on field DP_DST");
    			if(!flowConfiguration.getNw_dst().equals(""))
    				dstHostConfig.setNetworkAddress(flowConfiguration.getNw_dst());
    			else
    				throw new BadRequestException("Flow configuration error on field NW_DST");
        		dstHostConfig.setNodeType("OF");
        		if(!flowConfiguration.getLast_port().equals(""))
        			dstHostConfig.setNodeConnectorId(flowConfiguration.getLast_port());
    			else
    				throw new BadRequestException("Flow configuration error on field LAST_PORT");
    		}
   			//flowConfiguration.setPriority(Constants.BASE_FLOW_PRIORITY.toString());
   			//flowConfiguration.setEtherType("0x800");

    		ArrayList<Path> path = OdlUtilServices.findPaths(srcHostConfig, dstHostConfig);
    		if(path.size()>0){
	    		int r = OdlServices.installPathFlows(path.get(0), srcHostConfig, dstHostConfig, flowConfiguration);
	    		if(r<0)
	    			throw new BadRequestException("Error when pushing flow configuration");
	    		path.get(0).setFlow(flowConfiguration);
	    		TempData.installedPaths.add(path.get(0));
	    		directPath = path.get(0).getPathName();
	    		if(flowConfiguration.getBidirectional().equals("1")){
	    			path = OdlUtilServices.findPaths(dstHostConfig, srcHostConfig);
	        		r = OdlServices.installPathFlows(path.get(0), dstHostConfig, srcHostConfig, flowConfiguration);
	        		if(r<0)
	        			throw new BadRequestException("Error when pushing flow configuration");
	        		path.get(0).setFlow(flowConfiguration);
	        		TempData.installedPaths.add(path.get(0));
	        		reversePath = path.get(0).getPathName();
	    		}
	    		if(flowConfiguration.getBidirectional().equals("1"))
	        		return Utils.parseJsonToString(new CreateBidirectionalPathResponse(directPath, reversePath));
	    		else
	    			return Utils.parseJsonToString(new CreateDirectPathResponse(directPath));
    		}
    		throw new BadRequestException("No path found between input nodes");
    }



    @RequestMapping(value = "/virtualpath/{id}", method = RequestMethod.DELETE)
    public String deleteVirtualPath(@PathVariable String id) {
    	for(Path p : TempData.installedPaths){
    		if(id.equals(p.getPathName())){
    			OdlServices.removePathFlows(p);
    			TempData.installedPaths.remove(p);
    			return "Virtual path deleted";
    		}
    	}
    	throw new BadRequestException("Error, virtual path id not found");
    }



    @RequestMapping(value = "/virtualpath/{id}", method = RequestMethod.GET)
    public String getVirtualPath(@PathVariable String id) {
    	for(Path p : TempData.installedPaths){
    		if(id.equals(p.getPathName())){
    			return Utils.parseJsonToString(p);
    		}
    	}
    	throw new BadRequestException("Error, virtual path id not found");
    }


    @RequestMapping(value = "/virtualpath", method = RequestMethod.GET)
    public String getAllVirtualPaths() {
    	List<String> pathsName = new ArrayList<String>();
    	for(Path p : TempData.installedPaths)
    		pathsName.add(p.getPathName());
    	return Utils.parseJsonToString(new GetAllVirtualPathResponse(pathsName));
    }




    @RequestMapping(value = "/virtualpath/{pathId}/bandwidth/{bandwidth}", method = RequestMethod.GET)
    public String setPathBandwidth(@PathVariable String pathId, @PathVariable long bandwidth) {
    	if(bandwidth<=0)
    		throw new BadRequestException("Error, error on bandwidth parameter");
    	for(Path p : TempData.installedPaths){
    		if(pathId.equals(p.getPathName())){
    			OdlServices.modBandwidth(pathId, bandwidth);
    			//OdlServices.removePathFlows(p);
    			//TempData.installedPaths.remove(p);
    			//UPDATE BANDWIDTH VALUE
    			return "Virtual path modified";
    		}
    	}
    	throw new BadRequestException("Error, virtual path id not found");
    }





}