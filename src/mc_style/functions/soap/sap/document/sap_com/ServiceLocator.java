/**
 * ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mc_style.functions.soap.sap.document.sap_com;

class ServiceLocator extends org.apache.axis.client.Service implements mc_style.functions.soap.sap.document.sap_com.Service {

    public ServiceLocator() {
    }


    public ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ZGHTKUE_ANFBL
    private java.lang.String ZGHTKUE_ANFBL_address = "http://sap-server:8000/sap/bc/srt/rfc/sap/zghtkue_anfbl/006/zghtkue_anfbl/zghtkue_anfbl";

    public java.lang.String getZGHTKUE_ANFBLAddress() {
        return ZGHTKUE_ANFBL_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ZGHTKUE_ANFBLWSDDServiceName = "ZGHTKUE_ANFBL";

    public java.lang.String getZGHTKUE_ANFBLWSDDServiceName() {
        return ZGHTKUE_ANFBLWSDDServiceName;
    }

    public void setZGHTKUE_ANFBLWSDDServiceName(java.lang.String name) {
        ZGHTKUE_ANFBLWSDDServiceName = name;
    }

    public mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBL getZGHTKUE_ANFBL() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ZGHTKUE_ANFBL_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getZGHTKUE_ANFBL(endpoint);
    }

    public mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBL getZGHTKUE_ANFBL(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBLStub _stub = new mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBLStub(portAddress, this);
            _stub.setPortName(getZGHTKUE_ANFBLWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setZGHTKUE_ANFBLEndpointAddress(java.lang.String address) {
        ZGHTKUE_ANFBL_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBL.class.isAssignableFrom(serviceEndpointInterface)) {
                mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBLStub _stub = new mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBLStub(new java.net.URL(ZGHTKUE_ANFBL_address), this);
                _stub.setPortName(getZGHTKUE_ANFBLWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ZGHTKUE_ANFBL".equals(inputPortName)) {
            return getZGHTKUE_ANFBL();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "service");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZGHTKUE_ANFBL"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ZGHTKUE_ANFBL".equals(portName)) {
            setZGHTKUE_ANFBLEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
