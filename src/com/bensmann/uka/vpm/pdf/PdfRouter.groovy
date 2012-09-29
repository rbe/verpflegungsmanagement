/**
 * /Users/rbe/project/vpm/src/com/bensmann/uka/vpm/pdf/PdfRouter.groovy
 * 
 * Copyright (C) 2010 Informationssysteme Ralf Bensmann.
 * Alle Rechte vorbehalten. Nutzungslizenz siehe http://www.bensmann.com/license_de.html
 * All Rights Reserved. Use is subject to license terms, see http://www.bensmann.com/license_en.html
 * 
 * Created by: rbe
 */
package com.bensmann.uka.vpm.pdf

import com.bensmann.groovy.helper.CifsCategory
import com.bensmann.uka.vpm.*

/**
 * 
 */
class PdfRouter extends org.apache.camel.language.groovy.GroovyRouteBuilder {
	
	def from
	def to
	def filter
	def process
	
	/**
	 * 
	 */
	protected void configure() {
		// Incoming
		from("file://${VpmConfig["filestore.local.incoming.url"]}?noop=true&idempotent=true") 
		.filter {
			!it.in.headers.CamelFileName.startsWith(".") &&
			it.in.headers.CamelFileName.endsWith(".pdf")
		}
		.to("bean:pdfProcessor?method=receive")
		.to("bean:pdfProcessor?method=watchFileTime")
		// Outgoing
		from("file://${VpmConfig["filestore.local.outgoing.url"]}?noop=true&idempotent=true")
		.filter {
			!it.in.headers.CamelFileName.startsWith(".") &&
			it.in.headers.CamelFileName.endsWith(".pdf")
		}
		.to("bean:pdfProcessor?method=green")
		// Sending
		from("file://${VpmConfig["filestore.local.sending.url"]}?noop=true&idempotent=true")
		.filter {
			!it.in.headers.CamelFileName.startsWith(".") &&
			it.in.headers.CamelFileName.endsWith(".pdf")
		}
		.to("bean:pdfProcessor?method=send")
		.to("bean:pdfProcessor?method=remove")
	}
	
}
