/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/pdf/IncomingPdfProcessor.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.pdf

import com.bensmann.groovy.helper.GroovyHelper as GH
import com.bensmann.uka.vpm.*
import com.bensmann.uka.vpm.ui.*
import com.bensmann.uka.vpm.sap.*

/**
 * Process incoming PDF files.
 */
@Singleton
class PdfProcessor {
	
	def static TODAY = GH.clearTime(new java.util.Date())
	
	/**
	 * Receives all PDFs without 'x' at the end of filename.
	 * Update UI with information about PDF to start Adobe Reader.
	 */
	def receive(File file) {
		try {
			// Split filename into components
			def (fWard, fDate, fAnfrd) = (file.name - ".pdf").split("_")
			// Check date: >= today
			def pdfDate = java.util.Date.parse("yyyyMMdd", fDate)
			if (GH.compareDay(pdfDate, TODAY) >= 0) {
				println "receive: ${file}"
				// Ask for more data we got from SAP web service
				def sap = SAPRequests.instance.data.find {
					it.anfrd == fAnfrd
				}
				if (sap) {
					// Add room to ward
					def room = Ward.instance.getRoom(sap.raum)
					if (!room) {
						room = new Room(number: sap.raum)
						Ward.instance.addRoom(room)
					}
					// Add patient to room
					room.addPatient(new Patient(patno: sap.anfrd, name: sap.bezei, todoPdfFile: file))
					// Update tree
					MainFrame.instance.updateWardTree()
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// Ignore: seems to be a wrong filename...
		}
		// Return file
		file
	}
	
	/**
	 * Start thread to watch file changed time. If time has changed, copy PDF file
	 * into out folder.
	 */
	def watchFileTime(File file) {
		// Tweak java.io.File for SMB
		com.bensmann.groovy.helper.CifsFileInjector.inject()
		// Watch the file in a thread
		def r = { ->
			def outFolder = VpmConfig["filestore.local.outgoing.url"] as String
			// Remember last modified
			def lm = file.lastModified()
			// As long as the file exists...
			println "watchFileTime: start watching ${file}"
			while (file.exists()) {
				// Check file modification time and compare to save value
				if (file.lastModified() != lm) {
					// Remember last modified
					lm = file.lastModified()
					// Copy to out folder
					println "watchFileTime: ${file} has been modified, copying to ${outFolder}"
					file.copyTo(new java.io.File(outFolder, file.name))
				}
				// Wait some time
				try { Thread.sleep(1 * 1000) } catch (e) {
					e.printStackTrace()
				}
			}
			println "watchFileTime: stop watching ${file}"
		} as java.lang.Runnable
		new java.lang.Thread(r).start()
		// Return file
		file
	}
	
	/**
	 * 
	 */
	def green(File file) {
		// Split filename into components
		def (fWard, fDate, fAnfrd) = (file.name - ".pdf").split("_")
		// Ask for more data we got from SAP web service
		def sap = SAPRequests.instance.data.find {
			it.anfrd == fAnfrd
		}
		// Find patient
		def patient = Ward.instance.getRoom(sap.raum).getPatient(sap.anfrd)
		// Set donePdfFile
		patient.donePdfFile = file
		// Return file
		file
	}
	
	/**
	 * Transfer a PDF to remote filestore.
	 */
	def send(File file) {
		// Copy file to remote filestore
		def ntlmAuth =
				new jcifs.smb.NtlmPasswordAuthentication(
					VpmConfig["filestore.remote.domain"] ?: "",
					VpmConfig["filestore.remote.username"] ?: "",
					VpmConfig["filestore.remote.password"] ?: ""
				)
		def to = new java.io.File("${VpmConfig["filestore.remote.outgoing.url"]}", file.name)
		println "send: moving ${file} to ${to}"
		try {
			file.ntlmAuth = to.ntlmAuth = ntlmAuth
			file.moveTo(to)
		} catch (jcifs.smb.SmbException e) {
			// Ignore? Cannot create a file when that file already exists
		}
		// Return file
		file
	}
	
	/**
	 * Remove a file.
	 */
	def remove(File file) {
		println "remove: removing ${file}: ${file.delete()}"
		// Return file
		file
	}
	
}
