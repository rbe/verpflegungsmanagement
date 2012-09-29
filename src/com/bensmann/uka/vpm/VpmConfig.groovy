/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/VpmConfig.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm

/**
 * Manage configuration settings.
 */
class VpmConfig {
	
	static java.util.Properties prop
	
	static {
		// Load properties
		try {
			def f = new File("conf/vpm.properties")
			prop = new java.util.Properties()
			prop.load(f.newInputStream())
		} catch (e) {
			e.printStackTrace()
		}
	}
	
	def static get(String k) {
		def v = prop.getProperty(k)
		// Add working directory to filestore.local
		if (k.startsWith("filestore.local")) {
			"${System.getProperty("user.dir").replace('\\', '/')}/${v}"
		} else {
			v
		}
	}
	
	def static set(String k, v) {
		prop.put(k, v)
	}
	
}