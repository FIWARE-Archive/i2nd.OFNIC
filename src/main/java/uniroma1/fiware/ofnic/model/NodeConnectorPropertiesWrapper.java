package uniroma1.fiware.ofnic.model;

@javax.xml.bind.annotation.XmlType (
		  name = "list",
		  namespace = ""
		)
		@javax.xml.bind.annotation.XmlRootElement (
		  name = "list",
		  namespace = ""
		)

public class NodeConnectorPropertiesWrapper {

	  private java.util.List<org.opendaylight.controller.switchmanager.northbound.NodeConnectorProperties> _nodeConnectorProperties;

	  /**
	   * (no documentation provided)
	   */
	  @javax.xml.bind.annotation.XmlElement (
	    name = "nodeConnectorProperties",
	    namespace = ""
	  )
	  public java.util.List<org.opendaylight.controller.switchmanager.northbound.NodeConnectorProperties> getNodeConnectorProperties() {
	    return this._nodeConnectorProperties;
	  }

	  /**
	   * (no documentation provided)
	   */
	  public void setNodeConnectorProperties(java.util.List<org.opendaylight.controller.switchmanager.northbound.NodeConnectorProperties> _nodeConnectorProperties) {
	    this._nodeConnectorProperties = _nodeConnectorProperties;
	  }

	}