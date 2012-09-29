package mc_style.functions.soap.sap.document.sap_com;

class ZGHTKUE_ANFBLProxy implements mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBL {
  private String _endpoint = null;
  private mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBL zGHTKUE_ANFBL = null;
  
  public ZGHTKUE_ANFBLProxy() {
    _initZGHTKUE_ANFBLProxy();
  }
  
  public ZGHTKUE_ANFBLProxy(String endpoint) {
    _endpoint = endpoint;
    _initZGHTKUE_ANFBLProxy();
  }
  
  private void _initZGHTKUE_ANFBLProxy() {
    try {
      zGHTKUE_ANFBL = (new mc_style.functions.soap.sap.document.sap_com.ServiceLocator()).getZGHTKUE_ANFBL();
      if (zGHTKUE_ANFBL != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zGHTKUE_ANFBL)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zGHTKUE_ANFBL)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zGHTKUE_ANFBL != null)
      ((javax.xml.rpc.Stub)zGHTKUE_ANFBL)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBL getZGHTKUE_ANFBL() {
    if (zGHTKUE_ANFBL == null)
      _initZGHTKUE_ANFBLProxy();
    return zGHTKUE_ANFBL;
  }
  
  public void zghtkueAnfblManf(java.lang.String ivStation, mc_style.functions.soap.sap.document.sap_com.holders.ZghtkueManfTAifHolder etManf, javax.xml.rpc.holders.StringHolder evReturn) throws java.rmi.RemoteException{
    if (zGHTKUE_ANFBL == null)
      _initZGHTKUE_ANFBLProxy();
    zGHTKUE_ANFBL.zghtkueAnfblManf(ivStation, etManf, evReturn);
  }
  
  
}