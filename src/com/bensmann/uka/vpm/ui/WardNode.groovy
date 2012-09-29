/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/ui/WardNode.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.ui

/**
 * A ward node in the tree.
 */
class WardNode {
	
	/**
	 * Ward's name.
	 */
	String name
	
	/**
	 * 
	 */
	String toString() {
		"Station ${name}"
	}
	
}
