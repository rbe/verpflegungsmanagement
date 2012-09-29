/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/ui/UploadAction.groovy
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

/**
 * Upload PDFs to SAP using SMB filestore.
 */
@Singleton
class UploadAction {
	
	/**
	 * 
	 */
	def perform = { arg = null ->
		// Tweak java.io.File for SMB
		com.bensmann.groovy.helper.CifsFileInjector.inject()
		// Remove local filestore for to-be-send files
		new java.io.File(VpmConfig["filestore.local.outgoing.url"]).listFiles().each {
			def f = new java.io.File(VpmConfig["filestore.local.sending.url"], it.name)
			println "UploadAction/perform: ${it} -> ${f}"
			try {
				it.moveTo(f)
			} catch (e) {
				e.printStackTrace()
			}
		}
		// Clear ward
		Ward.instance.clear()
		// Update tree
		MainFrame.instance.roomNodeCache = [:]
		MainFrame.instance.updateWardTree()
	}
	
}
