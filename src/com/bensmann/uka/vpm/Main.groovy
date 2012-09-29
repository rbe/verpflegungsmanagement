/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/ui/Main.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm

import com.bensmann.uka.vpm.pdf.*
import com.bensmann.uka.vpm.ui.*

/**
 * Start VPM.
 */
class Main {
	
	/**
	 * Apache Camel.
	 */
	def static camelCtx
	
	/**
	 * 
	 */
	def static cleanupLocalFilestore() {
		// Remove local filestore for incoming files
		new java.io.File(VpmConfig["filestore.local.incoming.url"]).listFiles().each {
			it.delete()
		}
		// Remove local filestore for outgoing files
		new java.io.File(VpmConfig["filestore.local.outgoing.url"]).listFiles().each {
			it.delete()
		}
		// Remove local filestore for to-be-send files
		new java.io.File(VpmConfig["filestore.local.sending.url"]).listFiles().each {
			it.delete()
		}
	}
	
	/**
	 * Ride the camel...
	 */
	def static setupCamel() {
		// Context
		camelCtx = new org.apache.camel.impl.DefaultCamelContext()
		// Registry
		camelCtx.registry = new org.apache.camel.impl.SimpleRegistry()
		camelCtx.registry.registry.put("pdfProcessor", PdfProcessor.instance)
		// Routes
		camelCtx.addRoutes(new PdfRouter())
		// Start Camel
		camelCtx.start()
		// Register shutdown hook
		Runtime.runtime.addShutdownHook({ ->
			// Stop Camel
			camelCtx.stop()
			//
			cleanupLocalFilestore()
		})
	}
	
	/**
	 * 
	 */
	public static void main(String[] args) {
		// Start clean
		cleanupLocalFilestore()
		// Camel
		setupCamel()
		// Swing
		def mainFrame = MainFrame.instance.setupUI()
	}
	
}