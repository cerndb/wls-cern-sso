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
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;

public class JMXclient {

	private static JMXConnector connector;
	private static final String protocol = "t3";
	private static final String host = "localhost";
	private static final String port = "7001";
	private static final String username = "weblogic";
	private static final String password = "weblogic1";
	private static final String mserver = "weblogic.management.mbeanservers.domainruntime";

	public static void main(String[] args) throws IOException,
			MalformedObjectNameException, NullPointerException,
			AttributeNotFoundException, InstanceNotFoundException,
			MBeanException, ReflectionException {

		System.out.println("Opening connection to: " + protocol + "://" + host
				+ ":" + port + " with user " + username + " to mbean " + mserver);
		MBeanServerConnection mBeanServerConnection = initConnection(protocol,
				host, port, username, password, mserver);
		System.out.println("Default WLS domain: "
				+ mBeanServerConnection.getDefaultDomain());
		connector.close();
		System.out.println("Connection close");
	}

	/*
	 * Initialize connection to the Domain Runtime MBean Server.
	 */
	public static MBeanServerConnection initConnection(String protocol,
			String hostname, String portString, String username,
			String password, String mbserver) throws IOException,
			MalformedURLException {
		Integer portInteger = Integer.valueOf(portString);
		int port = portInteger.intValue();
		String jndiroot = "/jndi/";
		JMXServiceURL serviceURL = new JMXServiceURL(protocol, hostname, port,
				jndiroot + mbserver);
		Hashtable h = new Hashtable();
		h.put(Context.SECURITY_PRINCIPAL, username);
		h.put(Context.SECURITY_CREDENTIALS, password);
		h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES,
				"weblogic.management.remote");
		h.put("jmx.remote.x.request.waiting.timeout", new Long(10000));
		connector = JMXConnectorFactory.connect(serviceURL, h);
		return connector.getMBeanServerConnection();
	}

}
