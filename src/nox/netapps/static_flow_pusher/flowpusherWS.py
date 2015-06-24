# Copyright 2013 (C) Universita' di Roma La Sapienza
# 
# This file is part of OFNIC Uniroma GE.
# 
# OFNIC is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
# 
# OFNIC is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with OFNIC.  If not, see <http://www.gnu.org/licenses/>.
#
# @Author Federico Cimorelli
# @Email <cimorellifederico@gmail.com>

from nox.webapps.webservice      import webservice
from nox.webapps.webservice.neticws      import *
from nox.lib.core import Component
from nox.lib.netinet.netinet import datapathid
from nox.lib.netinet.netinet import create_eaddr
from nox.netapps.discovery.discoveryws import *
from nox.lib.packet.packet_utils import octstr_to_array
import simplejson as json
from twisted.web.http import Request
from nox.netapps.static_flow_pusher.flowpusher import StaticFlowPusher



class WSPathExistingFlowName(webservice.WSPathComponent):
    def __init__(self, flowpusher):
        webservice.WSPathComponent.__init__(self)
        self._flowpusher = flowpusher
    
    def __str__(self):
        return "{flow_ID}"
    
    def extract(self, flow, data):
        if flow == None:
            return webservice.WSPathExtractResult(error="Missing end of requested URI")
        try:
            flowID = flow
            pinfo=self._flowpusher.is_valid_flowid(flowID)
            if (pinfo == False): 
                e = "flow '%s' is unknown" % flow
                return webservice.WSPathExtractResult(error=e)
        except ValueError, e:        
            err = "unknown error %s"
            return webservice.WSPathExtractResult(error=err)
        return webservice.WSPathExtractResult(flow)



class flowpusherWS(Component):
    """Web service for static flow pusher commands"""

    def __init__(self, ctxt):
        Component.__init__(self, ctxt)

    def install(self):
        self.discoveryws = self.resolve(discoveryws)
        self.discovery = self.discoveryws.discovery
        self.flowpusher = self.resolve(StaticFlowPusher)
        ws  = self.resolve(str(webservice.webservice))
        v1  = ws.get_version("1")
        reg = v1.register_request
        rootpath = self.discoveryws.rootpath
        staticflow = rootpath + (webservice.WSPathStaticString("staticflow"),)
            
        # /ws.v1/netic/OFNIC/staticflow
        reg(self._netic_show_staticflow, "GET", staticflow,"""Get a list of currently installed static flow paths""","""Static flow Sub-Module""")

        # /ws.v1/netic/OFNIC/staticflow/create
        provisionpath = staticflow + (webservice.WSPathStaticString("create"),)
        reg(self._netic_create_staticflow, "POST", provisionpath, """Installs a static flow in the network.""")

        # /ws.v1/netic/OFNIC/staticflow/{id}
        flowIDpath = staticflow + (WSPathExistingFlowName(self.flowpusher),)
        reg(self._netic_remove_staticflow,"DELETE",flowIDpath, """Remove a previously created staticflow""")    
        reg(self._netic_get_staticflow,"GET",flowIDpath, """Retrieve static flow information""")

<<<<<<< HEAD
        #self.register_for_flow_removed ( lambda dp, flow : flowpusherWS.manage_flow_expired(self, dp, flow) )

=======
        # /ws.v1/netic/OFNIC/staticflow/clean/{id}
        dpid = staticflow + (webservice.WSPathStaticString("clean"),)
        reg(self._netic_clean_staticflows,"DELETE",dpid, """Remove all previously created staticflows on a switch""")    
        


        self.register_for_flow_removed ( lambda dp, flow : flowpusherWS.manage_flow_expired(self, dp, flow) )
>>>>>>> e9fc00805fb4c68cd5134c2aa3897b1b141f7957

    def _netic_clean_staticflows(self,request,arg):
        errorCode = {}
        a = {}
        attrs = {}
        dpid = str(arg['{dpid}'])
        #res = self.flowpusher.netic_clean_staticflows(dpid)
        #if(res == 1):
        #    neticResponse(request,NTC_OK,a)
        #else:
        #    neticResponse(request,NTC_ST_ERR_FLOW_NOT_FOUND)

    def _netic_remove_staticflow(self,request,arg):
        errorCode = {}
        a = {}
        attrs = {}
        flow_ID = str(arg['{flow_ID}'])
        res = self.flowpusher.netic_remove_staticflow(flow_ID)
        if(res == 1):
            neticResponse(request,NTC_OK,a)
        else:
            neticResponse(request,NTC_ST_ERR_FLOW_NOT_FOUND)


    def _netic_show_staticflow(self,request,arg):
        a = {}
        flowList = []
        flows = self.flowpusher.netic_get_staticflow_list()
        for flowID in flows:
            flowList.append(flowID)
        a['flowIDs'] = flowList
        neticResponse(request,NTC_OK,a)


    def _netic_get_staticflow(self,request,arg):
        errorCode = {}
        a = {}
        flow_ID = str(arg['{flow_ID}'])
        flows = self.flowpusher.netic_get_staticflow_list()
        for flowID in flows:
            if(flowID==flow_ID):
                f = flows[flowID]
                a['ID'] = flowID
                a['duration'] = f['duration']
                a['switch'] = f['switch']
                a['inport'] = f['inport']
                a['outport'] = f['outport'] 
                a['cookie'] = f['cookie'] 
                a['queue'] = f['queue']               
                neticResponse(request,NTC_OK,a)
        neticResponse(request,NTC_ST_ERR_FLOW_NOT_FOUND)
        return NOT_DONE_YET


    def _netic_create_staticflow(self,request,arg):
        #content = json_parse_message_body(arg)
        content = request.content.read()
        errorCode = {}
        try:
            flow_name = str(request.args['flow_name'][0])
            nw_src = str(request.args['nw_src'][0])
            nw_dst = str(request.args['nw_dst'][0])        
            duration = int(request.args['duration'][0])
            dp_id = int(str(request.args['dp_id'][0]))
            inport = int(request.args['inport'][0])
            outport = int(request.args['outport'][0])
            bidirectional = int(request.args['bidirectional'][0])
        except:
            errorCode['errorCode'] = NTC_VP_ERR_MISSING_M_FIELD
            webservice.badRequest(request,errcode2str[NTC_VP_ERR_MISSING_M_FIELD],errorCode)
            return NOT_DONE_YET   

        queueParams = request.args['queue'][0]   
        queue = ""        
        if(not queueParams == ""):
            queue = int(queueParams)    

        #check switch ID and switch's ports
        if (not self.discoveryws.discovery.is_valid_dpid(dp_id)):
            errorCode['errorCode'] = NTC_VP_ERR_UNKNOWN_SWITCH
            webservice.badRequest(request,errcode2str[NTC_VP_ERR_UNKNOWN_SWITCH],errorCode)
            return NOT_DONE_YET
        if (not self.discoveryws.discovery.is_valid_port_in_dpid(dp_id,inport) or not self.discoveryws.discovery.is_valid_port_in_dpid(dp_id, outport)):
            errorCode['errorCode'] = NTC_VP_ERR_UNKNOWN_PORT
            webservice.badRequest(request,errcode2str[NTC_VP_ERR_UNKNOWN_PORT],errorCode)
            return NOT_DONE_YET
        if((bidirectional!=0) and (bidirectional!=1)):
            errorCode['errorCode'] = NTC_VP_ERR_MISSING_M_FIELD
            webservice.badRequest(request,errcode2str[NTC_VP_ERR_MISSING_M_FIELD],errorCode)
            return NOT_DONE_YET

        tp_src = 0
        tp_dst = 0
        ip_proto = 255        
        granularity = True  
        try:
            ip_proto = int(request.args['ip_proto'][0])
        except:
            granularity = False
        if (granularity == True):
            try:
                tp_src = int(request.args['tp_src'][0])
            except:
                tp_src = 0
            try:
                tp_dst = int(request.args['tp_dst'][0])
            except:
                tp_dst = 0
        else:
            ip_proto = 255

        # At this point we have the mandatory params of the flow
        npi = Netic_path_info()
        if(nw_src == ""):
            npi.nw_src = "0.0.0.0"
        else:
            npi.nw_src = nw_src;
        if(nw_dst == ""):
            npi.nw_dst = "0.0.0.0"
        else:
            npi.nw_dst = nw_dst;
        npi.ip_proto = ip_proto
        npi.tp_src = tp_src
        npi.tp_dst = tp_dst
        npi.duration = duration

        npi.set_arp = True

<<<<<<< HEAD

        #if the new flow override the old, delete it from the local flow
        installedFlows = []
        installedFlows = self.flowpusher.netic_get_staticflow_list()
        idToDel = 0
        for flowID in installedFlows:
            flow = installedFlows[flowID] 
            if((flow['switch']==dp_id) and (flow['inport']==inport) and (flow['outport']==outport)):
                #self.remove_staticflow_from_db(flowID)
                idToDel = flowID
        self.flowpusher.netic_remove_local_staticflow(idToDel)
        s = flow_name+str(inport)+str(outport)
        cookie = int(self.flowpusher.calculate_cookie(dp_id, s),16)
        a = {}        
        a['direct_flow'] = self.flowpusher.netic_setup_openflow_packet(dp_id, inport, outport, queue, npi, cookie)     
        

        if(bidirectional==1):
            #if the new flow override the old, delete it from the local flow
            installedFlows = []
            installedFlows = self.flowpusher.netic_get_staticflow_list()
            idToDel = 0
            for flowID in installedFlows:
                flow = installedFlows[flowID] 
                if((flow['switch']==dp_id) and (flow['inport']==outport) and (flow['outport']==inport)):
                    #self.remove_staticflow_from_db(flowID)
                    idToDel = flowID
            self.flowpusher.netic_remove_local_staticflow(idToDel)
            cookie = int(self.flowpusher.calculate_cookie(dp_id, flow_name + str(outport) + str(queue) + str(inport)),16)
            a['inverse_flow'] = self.flowpusher.netic_setup_openflow_packet(dp_id, outport, inport, queue, npi, cookie)   

        neticResponse(request,NTC_OK,a) 
=======
        s = flow_name+str(inport)+str(outport)
        cookie = int(self.flowpusher.calculate_cookie(dp_id, s),16)
        a = {}        
        a['direct_flow'] = self.flowpusher.netic_setup_openflow_packet(dp_id, inport, outport, queue, npi, cookie)  
        if(bidirectional==1):
            cookie = int(self.flowpusher.calculate_cookie(dp_id, flow_name + str(outport) + str(queue) + str(inport)),16)
            a['inverse_flow'] = self.flowpusher.netic_setup_openflow_packet(dp_id, outport, inport, queue, npi, cookie)   
        
        neticResponse(request,NTC_OK,a)
>>>>>>> e9fc00805fb4c68cd5134c2aa3897b1b141f7957


    def manage_flow_expired(self, dp, flow):
        result = self.flowpusher.netic_remove_local_static_flow(flow['cookie'])


    def getInterface(self):
        return str(flowpusherWS)

def getFactory():
    class Factory:
        def instance(self, ctxt):
            return flowpusherWS(ctxt)
    return Factory()

class Netic_path_info:
    pass
