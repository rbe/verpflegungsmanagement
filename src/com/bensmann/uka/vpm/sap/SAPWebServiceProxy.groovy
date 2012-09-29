/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/sap/SAPWebServiceProxy.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.sap

import com.bensmann.uka.vpm.*
import mc_style.functions.soap.sap.document.sap_com.ZGHTKUE_ANFBLStub
import mc_style.functions.soap.sap.document.sap_com.ZghtkueManfSAif
import mc_style.functions.soap.sap.document.sap_com.holders.ZghtkueManfTAifHolder

/**
 * Proxy for SAP web service requests.
 */
class SAPWebServiceProxy {
	
	/**
	 * Test mode; don't call SAP just return generated requests.
	 */
	public static final Boolean TEST_MODE = VpmConfig["sap.service.test"] == "true"
	
	/**
	 * 
	 */
	def static generateTestData() {
		def sapResponse = []
		0.upto 3, { i ->
			def anfrd
			def raum
			switch (i) {
				case 0:
					anfrd = "0000100029"
					raum = "10"
					break
				case 1:
					anfrd = "0000100065"
					raum = "10"
					break
				case 2:
					anfrd = "0000100084"
					raum = "11"
					break
				case 3:
					anfrd = "0000100100"
					raum = "11"
					break
			}
			sapResponse << [
					id:      "test${i}",
					anfrd:   anfrd,
					bezei:   "Peter Patient ${anfrd}",
					datum:   java.util.Date.parse("yyyy-MM-dd", "2010-08-18"),
					/*
					falnr:   "falnr${i}",
					gebaude: "gebaude${i}",
					klinik:  "klinik${i}",
					mandt:   "mandt${i}",
					*/
					raum:    raum,
					station: "IM11",
					werks:   "300",
				]
		}
		// Debug
		sapResponse.each { println it }
		sapResponse
	}
	
	/**
	 * Ask SAP for meal requests.
	 * @return Map code, requests = [] with [:]
	 */
	static Map getMealRequests(sapUrl, username, password, ward) {
		def map = [:]
		//
		def sapResponse = []
		if (SAPWebServiceProxy.TEST_MODE) {
			println "getMealRequests: testmode"
			// Map
			map = [code: 200, response: generateTestData()]
		} else {
			try {
				// Setup web service
				org.apache.axis.client.Service service = new org.apache.axis.client.Service()
				ZGHTKUE_ANFBLStub stub = new ZGHTKUE_ANFBLStub(new java.net.URL(sapUrl), service)
				stub.setUsername(username)
				stub.setPassword(password)
				ZghtkueManfTAifHolder rtMantHolder = new ZghtkueManfTAifHolder()
				javax.xml.rpc.holders.StringHolder evReturnHolder = new javax.xml.rpc.holders.StringHolder("")
				// Call web serive
				stub.zghtkueAnfblManf(ward, rtMantHolder, evReturnHolder)
				// Process request
				def sapRequest
				if (evReturnHolder.value == null || evReturnHolder.value.equals("")) {
					sapRequest = rtMantHolder.value
				} else {
					println "getMealRequests: evReturnHolder.value=${evReturnHolder.value}"
				}
				sapRequest.each {
					def m = [
							id:      it.getID(),
							anfrd:   it.getANFRD(),
							bezei:   it.getBEZEI(),
							datum:   it.getDATUM(),
							/*
							falnr:   it.get(),
							gebaude: it.get(),
							klinik:  it.get(),
							mandt:   it.get(),
							*/
							raum:    it.getROOMNR(),
							station: it.getSTATION(),
							werks:   it.getWERKS()
						]
					println m
					sapResponse << m
				}
				// Map
				map = [code: 200, response: sapResponse]
			} catch (org.apache.axis.AxisFault e) {
				println "getMealRequests: could not call web service: " + e.getFaultCode()
				println e
				e.printStackTrace()
				// Map
				try {
					map = [code: e.faultString[1..3] as Integer, exception: e, response: []]
				} catch (e2) {
					map = [code: 500, exception: e2, response: []]
				}
			} catch (e) {
				map = [code: 500, exception: e, response: []]
			}
		}
		println "getMealRequests: done"
		map
	}
	
}
