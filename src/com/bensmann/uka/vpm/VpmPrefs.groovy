/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/VpmPrefs.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm

/**
 * 
 */
class VpmPrefs {
	
	static java.util.prefs.Preferences pref
	
	static {
		// Initialize user's preferences
		pref = java.util.prefs.Preferences.userNodeForPackage(VpmConfig.class)
	}
	
	def static get(String k) {
		def v = pref.get(k, null)
		v
	}
	
	def static set(String k, v) {
		pref.put(k, v)
	}
	
}