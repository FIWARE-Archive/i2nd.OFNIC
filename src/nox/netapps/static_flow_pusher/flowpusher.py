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


import logging
from nox.lib.core import *
from nox.lib.openflow import *
from nox.lib.packet.packet_utils      import ip_to_str
import simplejson as json
import hashlib
import random
from nox.coreapps.db_manager import mysql_manager
import datetime



lg = logging.getLogger('flowpusher')

class StaticFlowPusher(Component):

    installedFlows = {}

    def __init__(self, ctxt):
        Component.__init__(self, ctxt)


    def install(self):
        self.manager=self.resolve(str(mysql_manager.MySQLManager))
        #self.register_for_datapath_join (lambda dp,stats : self.dp_join(dp, stats) )


    def netic_setup_openflow_packet(self, dp_id, inport, outport, queue, npi, cookie):
        if(npi.nw_src == "0.0.0.0"):             
            attrs = {core.IN_PORT : inport,
                #core.DL_TYPE : ethernet.ethernet.IP_TYPE,
                core.NW_SRC : npi.nw_src,
                core.NW_DST : npi.nw_dst,
                core.NW_SRC_N_WILD : OFPFW_NW_SRC_ALL, 
                core.NW_DST_N_WILD : OFPFW_NW_DST_ALL
                }  
        else:
            attrs = {core.IN_PORT : inport,
                #core.DL_TYPE : ethernet.ethernet.IP_TYPE,
                core.NW_SRC : npi.nw_src,
                core.NW_DST : npi.nw_src
                }  

        if not (npi.ip_proto == 255):
            attrs[core.NW_PROTO] = npi.ip_proto
            attrs[core.TP_SRC] = npi.tp_src
            attrs[core.TP_DST] = npi.tp_dst
    
        idle_timeout = openflow.OFP_FLOW_PERMANENT
        hard_timeout = npi.duration

        if (queue == ""):
            actions = [[openflow.OFPAT_OUTPUT, [0, outport]]]
        else:
            actions = [[openflow.OFPAT_ENQUEUE, [0, outport, queue]]]
        
        print ("***try to install flow with dp_id: "+ 
               str(dp_id) +" inport: "+ str(inport) +" outport: " + 
               str(outport) )
        # str(npi.nw_src) +" dst_ip: " + str(npi.nw_dst) +" ***")
        res = self.install_datapath_flow(dp_id, attrs,
               idle_timeout, hard_timeout,actions,cookie)

        flowID = self.calculate_path_id(npi, inport, outport)
        self.installedFlows[flowID] = {}    
        self.installedFlows[flowID]["duration"] = npi.duration
        self.installedFlows[flowID]["flowID"] = flowID
        self.installedFlows[flowID]["switch"] = dp_id
        self.installedFlows[flowID]["inport"] = inport
        self.installedFlows[flowID]["outport"] = outport
        self.installedFlows[flowID]["cookie"] = cookie
        self.installedFlows[flowID]["nw_src"] = npi.nw_src
        self.installedFlows[flowID]["nw_dst"] = npi.nw_dst
        self.installedFlows[flowID]["ip_proto"] = npi.ip_proto  
        self.installedFlows[flowID]["tp_src"] = npi.tp_src
        self.installedFlows[flowID]["tp_dst"] = npi.tp_dst
        self.installedFlows[flowID]["queue"] = queue
        
        self.save_staticflow_to_db(flowID, self.installedFlows[flowID])
    
        return flowID 


    def netic_get_staticflow_list(self):
        return self.installedFlows

    
    def is_valid_flowid(self, fID):
        for flowID in self.installedFlows:
            if(flowID==fID):
                return "true"
        return "false"


    def netic_remove_staticflow(self, flow_ID):
        for fID in self.installedFlows:
            flow = self.installedFlows[fID] 
            if(flow['flowID']==flow_ID):               
                attrs = {}
                attrs[core.NW_SRC] = flow['nw_src']
                attrs[core.NW_DST] = flow['nw_dst']
                attrs[core.IN_PORT] = flow['inport']
                #attrs[core.DL_TYPE] = ethernet.ethernet.IP_TYPE
                if(flow['nw_src'] == "0.0.0.0"):             
                    attrs[core.NW_SRC_N_WILD] = OFPFW_NW_SRC_ALL, 
                    attrs[core.NW_DST_N_WILD] = OFPFW_NW_DST_ALL
                if not (flow['ip_proto'] == 255):
                    attrs[core.NW_PROTO] = flow['ip_proto']
                    attrs[core.TP_SRC] = flow['tp_src']
                    attrs[core.TP_DST] = flow['tp_dst']
    
                self.delete_strict_datapath_flow(flow['switch'], attrs)
                del self.installedFlows[flow_ID]
                self.remove_staticflow_from_db(flow_ID)
    
                return 1   
        return -1
<<<<<<< HEAD

    def netic_remove_local_staticflow(self, fid):
        for flowID in self.installedFlows:
            flow = self.installedFlows[flowID] 
            if(flow['flowID']==fid):
                #self.remove_staticflow_from_db(flowID)
                del self.installedFlows[fid]
                #self.installedFlows.remove(flowID)
                return    

=======
>>>>>>> e9fc00805fb4c68cd5134c2aa3897b1b141f7957

    def netic_remove_local_static_flow(self, cookie_flow):
        for flowID in self.installedFlows:
            flow = self.installedFlows[flowID] 
            if(flow['cookie']==cookie_flow):
                self.remove_staticflow_from_db(flowID)
                #del self.installedFlows[flow_ID]
                self.installedFlows.remove(flowID)
                self.remove_staticflow_from_db(flow_ID)
                return                


    def calculate_cookie(self, dp, pathID):
        m = hashlib.md5()
        stringToMd5 = str(dp) + str(pathID)
        m.update(stringToMd5)
        outLarge = m.hexdigest()
        #return the truncation to the first 16 hex chars. 
        return outLarge[:16]


    def calculate_path_id(self, npi, inport, outport):
        m = hashlib.md5()
        stringToMd5 = (str(npi.nw_src) + str(npi.nw_dst) +
                       str(npi.ip_proto) + str(inport) + str(outport))
        m.update(stringToMd5)
        #return the truncation to the first 16 hex chars. TODO
        
        #return m.hexdigest()[:4]
        return m.hexdigest()[:4] + str(random.randrange(1000,9999))

    
    def save_staticflow_to_db(self,flowID, flow):
        res = self.manager.save_staticflow(flowID,flow)
		  
              


    def remove_staticflow_from_db(self, flowID):
        res = self.manager.remove_staticflow(flowID)


<<<<<<< HEAD
    #def clean_staticflows(self, dpid): 
    #    flows = self.manager.load_staticflow(str(dpid))
    #    for f in flows:
    #        print "Deleting flow with ID: " + str(f['id'])
    #        self.remove_staticflow_from_db(f['id'])
    #        self.remove_staticflow(f['id'])  
=======
    def clean_staticflows(self, dpid): 
        flows = self.manager.load_staticflow(str(dpid))
        for f in flows:
            print "Deleting flow with ID: " + str(f['id'])
            self.remove_staticflow_from_db(f['id'])
            self.remove_staticflow(f['id'])  
>>>>>>> e9fc00805fb4c68cd5134c2aa3897b1b141f7957

        
    def dp_join(self, dp, stats):
        flows = self.manager.load_staticflow(str(dp))
        for f in flows:
            print "Loading saved flow from DB with ID: " + str(f['id'])
            print "Checking its validity..."
            npi = Netic_path_info()
            npi.nw_src = str(f['nw_src'])
            npi.nw_dst = str(f['nw_dst'])
            npi.ip_proto = int(f['ip_proto'])
            npi.tp_src = str(f['tp_src'])
            npi.tp_dst = str(f['tp_dst'])
            npi.duration = int(f['duration'])

            ts = str(f['date_time'])
            format = '%Y-%m-%d %H:%M:%S'
            creationTime = datetime.datetime.strptime(ts, format) 
            delta = datetime.datetime.now() - creationTime
            if (delta > datetime.timedelta(seconds=int(f['duration']))):
				print "Flow is expired"
				self.remove_staticflow_from_db(str(f['id']))
                #del self.installedFlows[str(f['id'])]
				self.netic_remove_staticflow(str(f['id']))
            else:
                print "Flow is installed"
                self.netic_setup_openflow_packet(int(dp), int(f['inport']), int(f['outport']), int(f['queue']), npi, int(f['cookie']))   



    def getInterface(self):
        return str(StaticFlowPusher)


def getFactory():
    class Factory:
         def instance(self, ctxt):
             return StaticFlowPusher(ctxt)   
    return Factory()

class Netic_path_info:
    pass
