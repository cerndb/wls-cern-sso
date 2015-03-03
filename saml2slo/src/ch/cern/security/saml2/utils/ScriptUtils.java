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
package ch.cern.security.saml2.utils;

import java.io.IOException;
import java.io.InputStream;

public class ScriptUtils {

	public static String getValueFromScript(String scriptName, String parameter)
			throws IOException {

		String[] command = { scriptName, parameter };

		// Execute command
		Process child = Runtime.getRuntime().exec(command);

		// Get the input stream and read from it
		InputStream in = child.getInputStream();
		int c;
		StringBuffer buffer = new StringBuffer();
		while ((c = in.read()) != -1) {
			buffer.append((char) c);
		}
		in.close();
		
		return buffer.toString();
	}
}
