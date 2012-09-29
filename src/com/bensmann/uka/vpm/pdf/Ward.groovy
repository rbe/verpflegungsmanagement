/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/pdf/Ward.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.pdf

/**
 * A ward has rooms.
 */
@Singleton
class Ward {
	
	/**
	 * Name of ward.
	 */
	String name
	
	/**
	 * A list of rooms.
	 */
	def room = [] as SortedSet
	
	/**
	 * Clear rooms.
	 */
	def clear() {
		room.clear()
	}
	
	/**
	 * Add a room.
	 */
	def addRoom(room) {
		this.room << room
	}
	
	/**
	 * Get a certain room by number.
	 */
	def getRoom(number) {
		room.find { it.number == number }
	}
	
	/**
	 * 
	 */
	String toString() {
		"${name}"
	}
	
}
