package uniroma1.fiware.ofnic.utils;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.opendaylight.controller.forwardingrulesmanager.FlowConfig;
import org.opendaylight.controller.sal.match.Match;
import org.opendaylight.controller.sal.match.MatchField;
import org.opendaylight.controller.sal.reader.FlowOnNode;

import uniroma1.fiware.ofnic.controllers.SynchronizeController;
import uniroma1.fiware.ofnic.data.TempData;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



public class Utils {

	static final Logger logger = Logger.getLogger(SynchronizeController.class);

	public static String getOutputPortFromAction(String a) {
		if(a.startsWith("OUTPUT"))
			return a.split("=")[1];
		String aux = a.split("=")[1];
		return aux.split(":")[0];
	}



	public static int getMatchingQueue(String bandwidth) {
		long bw = Long.parseLong(bandwidth);
		for(int i=0; i<TempData.queueConfiguration.size()-1; i++)
			if(TempData.queueConfiguration.get(i)>=bw)
				return i;
		return TempData.queueConfiguration.size()-1;
	}



    public static String parseJsonToString(Object o){
    	ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
		mapper.setSerializationInclusion(Include.ALWAYS); //was NOT_EMPTY
		String res = "";
		try {
			res = mapper.writeValueAsString(o);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return res;
    }


    public static boolean isMatch(FlowConfig fc, FlowOnNode fon){
    	Match match = fon.getFlow().getMatch();
    	for(MatchField mf : match.getMatchFields()){
    		logger.info(mf.toString());
    	}
		return false;
    }

}