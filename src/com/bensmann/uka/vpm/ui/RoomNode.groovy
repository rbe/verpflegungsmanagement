/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/ui/RoomNode.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.ui

/**
 * A room node in the tree.
 */
class RoomNode {
	
	/**
	 * Room's number.
	 */
	String number
	
	/**
	 * 
	 */
	String toString() {
		"Zimmer ${number ?: "?"}"
	}
	
}
