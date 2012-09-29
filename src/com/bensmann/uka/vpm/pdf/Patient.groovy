/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/pdf/Patient.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.pdf

/**
 * A patient.
 */
class Patient implements java.lang.Comparable {
	
	/**
	 * Patient number - SAP ANFRD.
	 */
	String patno
	
	/**
	 * Patients' name.
	 */
	String name
	
	/**
	 * Associated PDF 'todo'.
	 */
	java.io.File todoPdfFile
	
	/**
	 * Associated PDF 'done'.
	 */
	java.io.File donePdfFile
	
	int hashCode() {
		patno.hashCode() + name.hashCode() + (todoPdfFile.hashCode() ? todoPdfFile.hashCode() : 0)
	}
	
	boolean equals(Object other) {
		hashCode() == other.hashCode()
	}
	
	int compareTo(Object other) {
		hashCode() == other.hashCode() ? 0 : 1
	}
	
	/**
	 * Return String representation of patient, for TableModel.
	 */
	String toString() {
		/*
		try {
			"${name ?: "?"}, ${birthday.format("dd.MM.yyyy")}"
		} catch (e) {
			"${name ?: "?"}, ?"
		}
		*/
		"${name ?: "?"}"
	}
	
}
