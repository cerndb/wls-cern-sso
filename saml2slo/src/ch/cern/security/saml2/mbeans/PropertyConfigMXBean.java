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

import java.io.IOException;
import java.util.Map;

public interface PropertyConfigMXBean {

	public String setProperty(String key, String value) throws IOException;
    public String getProperty(String key);
    public Map getProperties();
}
