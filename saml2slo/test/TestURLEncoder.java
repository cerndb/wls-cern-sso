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


import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.stream.XMLStreamException;


public class TestURLEncoder {

	public static void main(String[] args) throws FileNotFoundException, XMLStreamException, UnsupportedEncodingException {
		System.out.println(URLEncoder.encode("http://www.w3.org/2000/09/xmldsig#rsa-sha1","iso-8859-1"));
		
		char[] temp = fixUrlEncode();
		
		System.out.println(new String(temp));
	}

	public static char[] fixUrlEncode() throws UnsupportedEncodingException {
		char[] temp = URLEncoder.encode("http://www.w3.org/2000/09/xmldsig#rsa-sha1","iso-8859-1").toCharArray();
		for (int i = 0; i < temp.length; i++) {
			if(temp[i] == '%'){
				temp[i+1] = Character.toLowerCase(temp[i+1]);
				temp[i+2] = Character.toLowerCase(temp[i+2]);
			}
		}
		return temp;
	}

}
