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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class FileRead {

	private static final String SLO_SERVICE_ENDPOINT = "<md:SingleLogoutService Binding=\"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect\" Location=\"https://your.domain.cern.ch/saml2slo/sp\"/>";

	public static void main(String args[]) {
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fileInputStream = new FileInputStream(
					"/home/luis/devIMS_A_1_sso.xml");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fileInputStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line and write it on another file
			FileWriter fileWriter = new FileWriter("/home/luis/devIMS_A_1_sso_slo.xml");
			BufferedWriter out = new BufferedWriter(fileWriter);
			while ((strLine = br.readLine()) != null) {
				out.write(strLine + "\n");
				if (strLine.contains("ArtifactResolutionService")) {
					out.write(SLO_SERVICE_ENDPOINT + "\n");
				}
			}
			// Close the input stream
			in.close();
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

}
