/**
 * /Users/rbe/project/sedar/src/com/bensmann/uka/sedar/WardTreeSelectionListener.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.ui

import com.bensmann.uka.vpm.pdf.*
import javax.swing.tree.DefaultMutableTreeNode as TreeNode

/**
 * 
 */
class WardTreeHelper {
	
	/**
	 * A room of a ward was selected in the tree. What patients do we have got?
	 */
	def static getRoomSelectionListener = { patientTableModel ->
		{ javax.swing.event.TreeSelectionEvent evt ->
			TreeNode tn = evt.paths[0].lastPathComponent
			if (tn.userObject instanceof RoomNode) {
				// Remove all rows from TableModel
				def rc = patientTableModel.rowCount
				if (rc > 0) {
					0.upto rc - 1, { patientTableModel.removeRow(0) }
				}
				// Add row/all patients to TableModel
				Ward.instance.getRoom(tn.userObject.number)?.patient?.sort { it.toString() }.each {
					patientTableModel.addRow(it)
				}
			}
			/*
			else {
				println "getRoomSelectionListener: tn.userObject.class != RoomNode: ${tn.userObject}"
			}
			*/
		} as javax.swing.event.TreeSelectionListener
	}
	
}
