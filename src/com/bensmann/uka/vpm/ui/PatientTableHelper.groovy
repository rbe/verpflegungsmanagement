/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/ui/PatientTableSelectionListener.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.ui

/**
 * 
 */
class PatientTableHelper  {
	
	/**
	 * 
	 */
	def static getSelectionListener = { patientTable ->
		{ javax.swing.event.ListSelectionEvent evt ->
			if (!evt.isAdjusting && patientTable.selectedRow > -1) {
				def item = patientTable.getValueAt(patientTable.selectedRow, 0)
				if (item.todoPdfFile && !item.donePdfFile) {
					//def f = new java.io.File("${item.todoPdfFile.parent}/camel", item.todoPdfFile.name)
					java.awt.Desktop.desktop.browse(item.todoPdfFile.toURI())
				} else if (item.todoPdfFile && item.donePdfFile) {
					java.awt.Desktop.desktop.browse(item.donePdfFile.toURI())
				}
			}
		} as javax.swing.event.ListSelectionListener
	}
	
}
