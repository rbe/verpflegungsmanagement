/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/pdf/Room.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.pdf

/**
 * A room has patients.
 */
class Room implements java.lang.Comparable {
	
	/**
	 * Number of room.
	 */
	String number
	
	/**
	 * A list of patient at this ward.
	 */
	def patient = [] as SortedSet
	
	/**
	 * Add a patient.
	 */
	def addPatient(patient) {
		this.patient << patient
	}
	
	/**
	 * Get a certain patient by number.
	 */
	def getPatient(patno) {
		patient.find { it.patno == patno }
	}
	
	int hashCode() {
		number.hashCode()
	}
	
	boolean equals(Object other) {
		hashCode() == other.hashCode()
	}
	
	int compareTo(Object other) {
		hashCode() == other.hashCode() ? 0 : 1
	}
	
	/**
	 * 
	 */
	String toString() {
		"${number ?: "?"}"
	}
	
}
