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
package ch.cern.security.saml2.mbeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class PropertyConfig implements PropertyConfigMXBean, MBeanRegistration {

//	private String relativePath = null;
	private Properties props = null;
	private File resource = null;

	public PropertyConfig(String relativePath) {
		this.props = new Properties();
//		this.relativePath = relativePath;
	}

	public void postDeregister() {}

	public void postRegister(Boolean arg0) {}

	public void preDeregister() throws Exception {}

	public ObjectName preRegister(MBeanServer server, ObjectName objectName)
			throws Exception {

		// MBean must be registered from an application thread
		// to have access to the application ClassLoader
//		ClassLoader cl = this.getClass().getClassLoader();
//		URL resourceUrl = cl.getResource(this.relativePath);
		this.resource = new File("/tmp/config/config.properties");
		this.load();
		return objectName;
	}

	public String setProperty(String key, String value) throws IOException {

		String oldValue = null;

		if (value == null) {
			oldValue = String.class.cast(this.props.remove(key));
		} else {
			oldValue = String.class.cast(this.props.setProperty(key, value));
		}

		this.save();
		return oldValue;
	}

	public String getProperty(String key) {

		return this.props.getProperty(key);
	}

	public Map getProperties() {

		return (Map) this.props;
	}

	private void load() throws IOException {

		InputStream is = new FileInputStream(this.resource);

		try {
			this.props.load(is);
		} finally {
			is.close();
		}
	}

	private void save() throws IOException {

		OutputStream os = new FileOutputStream(this.resource);

		try {
			this.props.store(os, null);
		} finally {
			os.close();
		}
	}

}
