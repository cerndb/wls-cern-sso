/*******************************************************************************
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * License version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 *
 *
 *******************************************************************************/
package ch.cern.security.saml2.jmx;

import java.security.cert.CertificateEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnectorFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import java.security.Principal;

import weblogic.security.Security;

import com.bea.security.saml2.providers.registry.WebSSOIdPPartner;

public class JmxUtils {

	public static String getDomainValue(String attributeName)
			throws NamingException, MalformedObjectNameException,
			NullPointerException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException {

		String value = null;
		InitialContext ctx = new InitialContext();
		MBeanServer server = (MBeanServer) ctx
				.lookup("java:comp/env/jmx/runtime");
		ObjectName objectName = new ObjectName("com.bea:Name="
				+ JmxConstants.RUNTIME_SERVICE + ",Type="
				+ JmxConstants.RUNTIME_SERVICE_MBEAN);
		ObjectName domainMBean = (ObjectName) server.getAttribute(objectName,
				JmxConstants.DOMAIN_CONFIGURATION);
		value = (String)server.getAttribute(domainMBean, attributeName);
		
		return value;
	}

	public static String getValue(String attributeName) throws NamingException,
			MalformedObjectNameException, NullPointerException,
			AttributeNotFoundException, InstanceNotFoundException,
			MBeanException, ReflectionException {

		String serverName = System.getProperty("weblogic.Name");
		InitialContext ctx = new InitialContext();
		MBeanServer server = (MBeanServer) ctx
				.lookup("java:comp/env/jmx/runtime");
		ObjectName objName = new ObjectName("com.bea:Name=" + serverName
				+ ",Type=Server");
		String value = (String) server.getAttribute(objName, attributeName);

		return value;
	}

	public static Object getCertificate(String authenticationProviderName,
			String webSSOpartnerName, String principal, String credentials) throws NamingException,
			MalformedObjectNameException, NullPointerException,
			AttributeNotFoundException, InstanceNotFoundException,
			MBeanException, ReflectionException, CertificateEncodingException {

		InitialContext ctx = null;
		
		Subject subject = Security.getCurrentSubject();
		Set<Principal> principals = null;
		if(subject!=null){
			principals = subject.getPrincipals();
		}

		if (principals == null || principals.size()==0) {
			Hashtable h = new Hashtable();
			h.put(Context.SECURITY_PRINCIPAL, principal);
			h.put(Context.SECURITY_CREDENTIALS, credentials);
			h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES,
					"weblogic.management.remote");
			ctx = new InitialContext(h);
		} else {
			
			for (Iterator iterator = principals.iterator(); iterator
					.hasNext();) {
				Principal p = (Principal) iterator.next();
				System.out.println(p.getClass().getCanonicalName() + " " + p.getName());
			}
			
			ctx = new InitialContext();
		}

		MBeanServer server = (MBeanServer) ctx
				.lookup("java:comp/env/jmx/runtime");

		ObjectName service = new ObjectName(
				"com.bea:Name=RuntimeService,"
						+ "Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");

		ObjectName domainMBean = (ObjectName) server.getAttribute(service,
				"DomainConfiguration");
		ObjectName securityConfiguration = (ObjectName) server.getAttribute(
				domainMBean, "SecurityConfiguration");
		ObjectName defaultRealm = (ObjectName) server.getAttribute(
				securityConfiguration, "DefaultRealm");
		ObjectName[] atnProviders = (ObjectName[]) server.getAttribute(
				defaultRealm, "AuthenticationProviders");

		ObjectName cernIdentityAsserter = null;

		for (int i = 0; i < atnProviders.length; i++) {
			String name = (String) server.getAttribute(atnProviders[i], "Name");
			if (name.equals(authenticationProviderName)) {
				cernIdentityAsserter = atnProviders[i];
			}
		}

		WebSSOIdPPartner idPPartner = null;
		if (cernIdentityAsserter != null) {
			idPPartner = (WebSSOIdPPartner) server.invoke(cernIdentityAsserter,
					"getIdPPartner", new Object[] { webSSOpartnerName },
					new String[] { "java.lang.String" });
		}

		byte[] encodedCertificate = null;
		if (idPPartner != null) {
			encodedCertificate = idPPartner.getSSOSigningCert().getEncoded();
		}

		return encodedCertificate;
	}

	public static Object getValue(String serviceName,
			String parentAttributeName, String attributeName, String objectType)
			throws NamingException, MalformedObjectNameException,
			NullPointerException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException {

		Object attributeValue = null;

		MBeanServer server;
		ObjectName service;

		// Get the Initial Context
		InitialContext ctx = new InitialContext();

		// Get the MbeanServer doing a local lookup
		server = (MBeanServer) ctx.lookup("java:comp/env/jmx/runtime");

		// Get the RuntimeServer MBean. we can navigate the hierarchy of
		// WebLogic Server runtime MBeans and active configuration MBeans for
		// the current server.
		service = new ObjectName("com.bea:Name=" + serviceName + ",Type="
				+ objectType);

		// Get the entire Domain Configuration
		ObjectName domainMbean = (ObjectName) server.getAttribute(service,
				JmxConstants.DOMAIN_CONFIGURATION);

		// Get the list of ServerMbeans available in this domain
		ObjectName[] serverMbean = (ObjectName[]) server.getAttribute(
				domainMbean, JmxConstants.SERVERS);
		int length = (int) serverMbean.length;

		// Gets the current server’s runtime information
		ObjectName serverRuntime = (ObjectName) server.getAttribute(service,
				JmxConstants.SERVER_RUNTIME);
		String serverName = (String) server.getAttribute(serverRuntime,
				JmxConstants.NAME);

		for (int i = 0; i < length; i++) {
			// Server Name
			String name = (String) server.getAttribute(serverMbean[i],
					JmxConstants.NAME);
			if (serverName.equals(name)) {
				ObjectName parentMbean = (ObjectName) server.getAttribute(
						serverMbean[i], parentAttributeName);
				attributeValue = server
						.getAttribute(parentMbean, attributeName);
				break;
			}
		}

		return attributeValue;
	}
}
