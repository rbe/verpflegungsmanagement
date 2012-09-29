/**
 * /Users/rbe/project/sedar/src/com/bensmann/uka/sedar/PatientTableCellRenderer.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.ui

import com.bensmann.uka.vpm.pdf.*

/**
 * How to render cells in patient table?
 */
class PatientTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
	
	def TODO_BGCOLOR          = java.awt.Color.RED.darker()
	def TODO_FGCOLOR          = java.awt.Color.WHITE
	def SELECTED_TODO_BGCOLOR = java.awt.Color.RED
	def SELECTED_TODO_FGCOLOR = java.awt.Color.WHITE
	
	def DONE_BGCOLOR          = java.awt.Color.GREEN.darker()
	def DONE_FGCOLOR          = java.awt.Color.BLACK
	def SELECTED_DONE_BGCOLOR = java.awt.Color.GREEN
	def SELECTED_DONE_FGCOLOR = java.awt.Color.BLACK
	
	/**
	 * 
	 */
	public java.awt.Component getTableCellRendererComponent (javax.swing.JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
		if (obj instanceof Patient) {
			java.awt.Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column)
			if (isSelected) {
				if (obj.donePdfFile) {
					cell.setBackground(SELECTED_DONE_BGCOLOR)
					cell.setForeground(SELECTED_DONE_FGCOLOR)
				} else {
					cell.setBackground(SELECTED_TODO_BGCOLOR)
					cell.setForeground(SELECTED_TODO_FGCOLOR)
				}
			} else {
				if (obj.donePdfFile) {
					cell.setBackground(DONE_BGCOLOR)
					cell.setForeground(DONE_FGCOLOR)
				} else {
					cell.setBackground(TODO_BGCOLOR)
					cell.setForeground(TODO_FGCOLOR)
				}
			}
			cell
		} else {
			println "getTableCellRendererComponent: obj.class != Patient: ${obj.dump()}"
		}
	}
	
}
