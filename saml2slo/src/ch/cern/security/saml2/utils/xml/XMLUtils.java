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
package ch.cern.security.saml2.utils.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.codec.binary.Base64;
import org.xml.sax.SAXException;

import weblogic.logging.NonCatalogLogger;

import ch.cern.security.saml2.vo.SamlVO;

/**
 * 
 * @author Luis Rodriguez Fernandez lurodrig@cern.ch
 *
 */
public class XMLUtils {
	
	/**
	 * Log see
	 * http://www.oracle.com/pls/fmw121100/to_URL?remark=ranked&urlname=http
	 * :%2F%2Fdownload.oracle.com%2Fdocs%2Fcd%2FE24329_01%2Fweb.1211%2Fe24419%2F
	 * writing.htm%23LOGSV159
	 */
	private static NonCatalogLogger nc = new NonCatalogLogger(XMLUtils.class.getName());

	public static String xmlDecodeAndInflate(String encodedXmlString, boolean isDebugEnabled)
			throws UnsupportedEncodingException, DataFormatException {

		// URL decode
		// No need to URL decode: auto decoded by request.getParameter() method (GET)
		
		if(isDebugEnabled)
			nc.notice("Encoded XML String: " + encodedXmlString);
		
		// Base64 decode
		Base64 base64Decoder = new Base64();
		byte[] xmlBytes = encodedXmlString.getBytes("UTF-8");
		byte[] base64DecodedByteArray = base64Decoder.decode(xmlBytes);
		
		if(isDebugEnabled)
			nc.notice("Base64 decoded bytes: " + new String(base64DecodedByteArray,"UTF-8"));
		
		// Inflate (uncompress) the AuthnRequest data
		// First attempt to unzip the byte array according to DEFLATE (rfc 1951)
		Inflater inflater = new Inflater(true);
		inflater.setInput(base64DecodedByteArray);
		// since we are decompressing, it's impossible to know how much space we
		// might need; hopefully this number is suitably big
		byte[] xmlMessageBytes = new byte[5000];
		int resultLength = inflater.inflate(xmlMessageBytes);

		if (!inflater.finished()) {
			throw new RuntimeException("didn't allocate enough space to hold "
					+ "decompressed data");
		}

		inflater.end();
		
		String decodedString = new String(xmlMessageBytes, 0, resultLength,
				"UTF-8");
		
		if(isDebugEnabled)
			nc.notice("Decoded and inflated string (UTF-8): " + new String(base64DecodedByteArray));

		return decodedString;
	}
	
	/**
	 * Compress the xml string and encodes it in Base64
	 * 
	 * @param xmlString
	 * @param isDebugEnabled 
	 * @return xml string encoded
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static String xmlDeflateAndEncode(String xmlString, boolean isDebugEnabled)
			throws IOException, UnsupportedEncodingException {
		
		if(isDebugEnabled)
			nc.notice(xmlString);
		
		// Deflate the SAMLResponse value
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		Deflater deflater = new Deflater(Deflater.DEFLATED, true);
		DeflaterOutputStream deflaterStream = new DeflaterOutputStream(
				bytesOut, deflater);
		deflaterStream.write(xmlString.getBytes());
		deflaterStream.finish();

		// Encoded the deflatedResponse in base64
		Base64 base64encoder = new Base64();
		String base64response = new String(base64encoder.encode(bytesOut
				.toByteArray()), "UTF-8");
		
		if(isDebugEnabled)
			nc.notice(base64response);
		
		return base64response;
	}
	
	public static String encode(String string)
	throws IOException, UnsupportedEncodingException {
		
		// Encoded the string in base64
		Base64 base64encoder = new Base64();
		String base64response = new String(base64encoder.encode(string.getBytes()),"UTF-8");
		
		return base64response;
	}
	
	/**
	 * Creates a SamlVO instance with the minimum data for performing the SLO  
	 * 
	 * @param xmlLogoutRequest
	 * @param isDebugEnabled
	 * @return SamlVO instance
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static SamlVO parseXMLmessage(String xmlLogoutRequest, boolean isDebugEnabled)
			throws ParserConfigurationException, SAXException, IOException {

		// Parse the XML. SAX approach, we just need the ID attribute
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

		// If we want to validate the doc we need to load the DTD
		// saxParserFactory.setValidating(true);

		// Get a SAXParser instance
		SAXParser saxParser = saxParserFactory.newSAXParser();

		// Parse it
		XMLhandler xmLhandler = new XMLhandler(isDebugEnabled);
		saxParser.parse(new ByteArrayInputStream(xmlLogoutRequest.getBytes()),
				xmLhandler);

		// Return the SamlVO
		return xmLhandler.getSamlVO();
	}
	
	/**
	 * Cretes the <samlp:LogoutResponse>
	 * 
	 * @param samlVO
	 * @param isDebugEnabled
	 * @return <samlp:LogoutResponse>
	 * @throws XMLStreamException
	 */
	public static String createXMLresponse(SamlVO samlVO, boolean isDebugEnabled) throws XMLStreamException {
		
		if(isDebugEnabled)
			nc.notice(samlVO.toString());
		
		// TODO: try with JAXB (check MW_HOME/modules/javax.xml.bind...)
		StringBuffer bufferResponse = new StringBuffer();
		
		// Start the samlp:LogoutReponse element, root
		bufferResponse.append("<samlp:LogoutResponse");
		// Add the ID
		bufferResponse.append(" ID=\"").append(samlVO.getId()).append("\"");
		// Add the version
		bufferResponse.append(" Version=\"").append("2.0").append("\"");
		// Add the IssueInstant
		bufferResponse.append(" IssueInstant=\"").append(samlVO.getIssueInstant()).append("\"");
		// Add the Destination: https://login.cern.ch/adfs/ls/, https://opendayslogin.cern.ch/adfs/ls/...
		bufferResponse.append(" Destination=\"").append(samlVO.getDestination()).append("\"");
		// Add the Consent
		bufferResponse.append(" Consent=\"").append("urn:oasis:names:tc:SAML:2.0:consent:unspecified").append("\"");
		// Add the InResponseTo
		bufferResponse.append(" InResponseTo=\"").append(samlVO.getInResponseTo()).append("\"");
		// Add the name space
		bufferResponse.append(" xmlns:samlp=\"").append("urn:oasis:names:tc:SAML:2.0:protocol").append("\"");
		// Close the element
		bufferResponse.append(">");
		
		// Start the Issuer element with the name space
		bufferResponse.append("<Issuer ").append(" xmlns=\"").append("urn:oasis:names:tc:SAML:2.0:assertion").append("\"").append(">");
		// Add the issuer value
		bufferResponse.append(samlVO.getIssuer());
		// Close the element
		bufferResponse.append("</Issuer>");
		
		// Start the Status element
		bufferResponse.append("<samlp:Status>");
		// Start the StatusCode element 
		bufferResponse.append("<samlp:StatusCode");
		// Add the Value
		bufferResponse.append(" Value=\"").append("urn:oasis:names:tc:SAML:2.0:status:Success").append("\"");
		// Close the element
		bufferResponse.append(" />");
		// Close the element
		bufferResponse.append("</samlp:Status>");
		
		// Close the element
		bufferResponse.append("</samlp:LogoutResponse>");
		
		if(isDebugEnabled)
			nc.notice(bufferResponse.toString());
		
		return bufferResponse.toString();
	}
}
