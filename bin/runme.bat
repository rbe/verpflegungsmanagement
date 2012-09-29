@echo off
set CP=lib/vpm.jar;lib/activation-1.1.jar;lib/axis-ant.jar;lib/axis.jar;lib/camel-core-2.4.0.jar;lib/camel-groovy-2.4.0.jar;lib/commons-discovery-0.2.jar;lib/commons-logging-1.1.1.jar;lib/commons-management-1.0.jar;lib/groovy-all-1.7.3.jar;lib/jaxrpc.jar;lib/jcifs-1.3.14.jar;lib/log4j-1.2.8.jar;lib/saaj.jar;lib/wsdl4j-1.5.1.jar
java -cp %CP% com.bensmann.uka.vpm.Main 
