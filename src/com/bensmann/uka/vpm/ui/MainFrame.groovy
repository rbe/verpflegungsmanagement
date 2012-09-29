/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/ui/MainFrame.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.ui

import com.bensmann.groovy.helper.GroovySwingHelper as GSH
import com.bensmann.uka.vpm.*
import com.bensmann.uka.vpm.pdf.*
import groovy.swing.SwingBuilder
import javax.swing.tree.DefaultMutableTreeNode as TreeNode

/**
 * The VPM user interface.
 */
@Singleton
class MainFrame {
	
	/**
	 * Height of rows (used for tree and table).
	 */
	def rowHeight = 60
	
	/**
	 * SwingBuilder.
	 */
	def swing = new groovy.swing.SwingBuilder()
	
	/**
	 * Frame created by SwingBuilder.
	 */
	javax.swing.JFrame frame
	
	/**
	 * The ultimate, usual, non-suprising progress bar.
	 */
	javax.swing.JProgressBar progressbar
	
	/**
	 * Cache room number -> RoomNode
	 */
	def roomNodeCache = [:]
	
	/**
	 * Tree.
	 */
	javax.swing.JTree wardTree
	
	/**
	 * Table showing patients.
	 */
	javax.swing.JTable patientTable
	
	/**
	 * 
	 */
	def makeFrame() {
		def screen = java.awt.Toolkit.defaultToolkit.screenSize
		def w = (screen.width * 0.97) as Integer
		def h = (screen.height * 0.97) as Integer
		frame = swing.frame(
			title: "VPM@UKA - Menueanforderung",
			defaultCloseOperation: javax.swing.JFrame.DISPOSE_ON_CLOSE,
			background: java.awt.Color.WHITE,
			size: [screen.width as int, screen.height as int],
			show: true,
			locationByPlatform: true, locationRelativeTo: null
		) {
			lookAndFeel("nimbus")
			panel() {
				borderLayout()
				scrollPane(constraints: CENTER, preferredSize: [w, h]) {
					panel(opaque: false) {
						borderLayout()
						panel(constraints: NORTH, preferredSize: [w, 75]) {
							borderLayout()
							label(constraints: WEST, icon: new javax.swing.ImageIcon(VpmResource["titel_uka.gif"]))
							panel(constraints: CENTER) {
								borderLayout()
								panel(constraints: NORTH) {
									label("SAP Benutzer:", preferredSize: [90, 30])
									textField(preferredSize: [150, 30], text: VpmPrefs["sap.username"], keyReleased: sapUsernameChanged, actionPerformed: downloadDataAction)
									passwordField(preferredSize: [150, 30], keyReleased: sapPasswordChanged, actionPerformed: downloadDataAction)
								}
								panel(constraints: SOUTH) {
									label("Station:", preferredSize: [90, 30])
									textField(preferredSize: [300, 30], keyReleased: wardNameChanged, actionPerformed: downloadDataAction)
								}
							}
							label(constraints: EAST, icon: new javax.swing.ImageIcon(VpmResource["titel_rwth.gif"]))
						}
						panel(constraints: WEST) {
							borderLayout()
							splitPane(constraints: WEST) {
								scrollPane(constraints: "left", preferredSize: [230, -1]) {
									wardTree = tree(rootVisible: false)
									// Remove all nodes (defaults: sports, color, ...)
									wardTree.model.root.removeAllChildren()
									// Reload model
									wardTree.model.reload(wardTree.model.root)
								}
								scrollPane(constraints: "top", preferredSize: [w - 230 - 15, -1]) {
									patientTable = table(rowHeight: rowHeight)
								}
							}
						}
						panel(constraints: SOUTH, preferredSize: [w, 100]) {
							borderLayout()
							button(constraints: WEST, icon: new javax.swing.ImageIcon(VpmResource["daten_laden.jpg"]),  actionPerformed: downloadDataAction)
							button(constraints: EAST, icon: new javax.swing.ImageIcon(VpmResource["daten_senden.png"]), actionPerformed: uploadDataAction)
						}
					}
				}
			}
		}
		//GSH.makeFrameFullScreen(frame)
		GSH.maximizeFrame(frame)
		frame
	}
	
	/**
	 * Ward name was changed by user -- remember it.
	 */
	def wardNameChanged = { evt ->
		def t = evt.source.text.toUpperCase()
		if (t != VpmPrefs["ward"]) {
			evt.source.text = t
			VpmPrefs["ward"] = evt.source.text
			Ward.instance.name = VpmPrefs["ward"]
		}
	}
	
	/**
	 * SAP username changed -- remember it.
	 */
	def sapUsernameChanged = { evt ->
		VpmPrefs["sap.username"] = evt.source.text
	}
	
	/**
	 * SAP password changed -- temporarily save it in config.
	 */
	def sapPasswordChanged = { evt ->
		VpmConfig["sap.password"] = evt.source.text
	}
	
	/**
	 * Download data from SAP using a web service and a filestore.
	 */
	def downloadDataAction = {
		SwingBuilder.build {
			doLater {
				def dialog = GSH.showProgressDialog(
					frame: frame,
					message: "Daten werden von SAP geholt.\n\nBitte warten..."
				)
				dialog.defaultCloseOperation = javax.swing.WindowConstants.DISPOSE_ON_CLOSE
				dialog.show()
				doOutside {
					// APP IS NOT RESPONSIBLE dialog.modal = true
					DownloadAction.instance.perform(frame: frame)
					doLater {
						dialog.dispose()
					}
				}
			}
		}
	}
	
	/**
	 * Upload PDFs to SAP using SMB filestore.
	 */
	def uploadDataAction = {
		def answer = GSH.showConfirmDialog(
			frame: frame,
			title: "Daten an SAP senden",
			message: "Alle ausgefuellten Formulare werden an SAP gesendet," +
					"\nalle anderen werden geloescht!" +
					"\n\nFortfahren?"
		)
		if (javax.swing.JOptionPane.YES_OPTION == answer) {
			SwingBuilder.build {
				doLater {
					def dialog = GSH.showProgressDialog(
						frame: frame,
						message: "Daten werden an SAP gesendet.\n\nBitte warten..."
					)
					dialog.show()
					doOutside {
						dialog.modal = true
						UploadAction.instance.perform(frame: frame, patientTableModel: patientTable.model)
						doLater {
							dialog.hide()
						}
					}
				}
			}
		}
	}
	
	/**
	 * Configure patient table.
	 */
	def configureTable() {
		// TableModel
		patientTable.model = new javax.swing.table.DefaultTableModel() {
				public boolean isCellEditable(int rowIndex, int vColIndex) {
					return false
				}
			}
		// Column "Patient"
		patientTable.model.addColumn("Patient")
		def col = patientTable.columnModel.getColumn(0)
		col.cellRenderer = new PatientTableCellRenderer()
		// ListSelectionListener
		patientTable.selectionModel.addListSelectionListener(PatientTableHelper.getSelectionListener(patientTable))
	}
	
	/**
	 * Configure ward tree.
	 */
	def configureTree() {
		// Configure tree
		wardTree.rowHeight = rowHeight
		wardTree.scrollsOnExpand = true
		wardTree.selectionModel = new javax.swing.tree.DefaultTreeSelectionModel()
		wardTree.selectionModel.selectionMode = javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION
		wardTree.toggleClickCount = 1
		// Add listeners to tree
		wardTree.addTreeSelectionListener(WardTreeHelper.getRoomSelectionListener(patientTable.model))
	}
	
	/**
	 * Update tree with rooms from Ward.instance.
	 */
	def updateWardTree() {
		// Remove all nodes
		wardTree.model.root.removeAllChildren()
		// Create ward node with rooms
		def w = Ward.instance
		if (w.room.size() > 0) {
			def node = new TreeNode(new WardNode(name: w.name))
			w.room.sort { it.toString() }.each { r ->
				if (!roomNodeCache[r.number]) {
					roomNodeCache[r.number] = new TreeNode(new RoomNode(number: r.number))
				}
				node.add(roomNodeCache[r.number])
			}
			// Add ward node to tree
			wardTree.model.root.add(node)
		}
		// Reload model
		wardTree.model.reload(wardTree.model.root)
	}
	
	/**
	 * Create frame, setup tree, show GUI.
	 */
	def setupUI() {
		makeFrame()
		configureTable()
		configureTree()
	}
	
}
