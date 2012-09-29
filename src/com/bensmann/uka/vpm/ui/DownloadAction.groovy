/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/ui/DownloadAction.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.ui

import com.bensmann.groovy.helper.GroovySwingHelper as GSH
import com.bensmann.uka.vpm.*
import com.bensmann.uka.vpm.pdf.*
import com.bensmann.uka.vpm.sap.*

/**
 * Download data from SAP using a web service and a filestore.
 */
@Singleton
class DownloadAction {
	
	/**
	 * 
	 */
	def perform = { arg = null ->
		// Tweak java.io.File for SMB
		com.bensmann.groovy.helper.CifsFileInjector.inject()
		try {
			// Call SAP web service; get meal requests
			def sapResponse =
				SAPWebServiceProxy.getMealRequests(
					VpmConfig["sap.service.endpoint"],
					VpmPrefs["sap.username"],
					VpmConfig["sap.password"],
					Ward.instance.name
				)
			SAPRequests.instance.data = sapResponse.response
			// Login ok?
			if (sapResponse.code != 200) {
				switch (sapResponse.code) {
					case 401:
						GSH.showErrorDialog(
							frame: arg.frame,
							title: "SAP: Daten holen",
							message: "Die Anmeldung an SAP ist fehlgeschlagen!",
							exception: SAPRequests.instance.data.exception
						)
						break
					case 500:
						GSH.showErrorDialog(
							frame: arg.frame,
							title: "SAP: Daten holen",
							message: "Es konnte keine Abfrage an SAP gesendet werden!",
							exception: SAPRequests.instance.data.exception
						)
						break
				}
			} else {
				// Cleanup local file store
				Main.cleanupLocalFilestore()
				// Copy PDFs from filestore
				def remote = VpmConfig["filestore.remote.incoming.url"]
				def remotePdfs = new java.io.File(remote)
				if (remote ==~ /smb.*/) {
					remotePdfs.ntlmAuth =
						new jcifs.smb.NtlmPasswordAuthentication(
							VpmConfig["filestore.remote.domain"],
							VpmConfig["filestore.remote.username"],
							VpmConfig["filestore.remote.password"]
						)
				}
				// Find all PDFs for ward; skip dot files
				try {
					def pdfs = remotePdfs.listFiles().findAll {
						!it.name.startsWith(".") &&
						it.name ==~ /.*${Ward.instance.name}_.*/
					}
					// If PDF files were found, copy them and update JTree
					if (pdfs) {
						pdfs.each {
							// TODO moveTo
							try {
								it.copyTo(new java.io.File(VpmConfig["filestore.local.incoming.url"], it.name))
							} catch (e) {
								e.printStackTrace()
							}
						}
					} else {
						GSH.showInfoDialog(
							frame: arg.frame,
							title: "SAP: Daten laden",
							message: "Es wurden keine Daten gefunden!"
						)
					}
				} catch (e) {
					GSH.showErrorDialog(
						frame: arg.frame,
						title: "SAP: Daten laden",
						message: "Die PDFs konnten nicht vom Server geholt werden!"
					)
				}
			}
		} catch (e) {
			GSH.showErrorDialog(
				frame: arg.frame,
				title: "SAP: Daten holen",
				message: "Leider konnte keine Daten geholt werden:\n${e}",
				exception: e
			)
		}
	}
	
}
