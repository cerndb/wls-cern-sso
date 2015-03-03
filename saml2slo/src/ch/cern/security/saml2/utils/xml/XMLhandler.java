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

import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import conf.Constants;

import weblogic.logging.NonCatalogLogger;

import ch.cern.security.saml2.vo.SamlVO;

/**
 * 
 * @author Luis Rodriguez Fernandez lurodrig@cern.ch
 *
 */
public class XMLhandler extends DefaultHandler {

	/**
	 * Log see
	 * http://www.oracle.com/pls/fmw121100/to_URL?remark=ranked&urlname=http
	 * :%2F%2Fdownload.oracle.com%2Fdocs%2Fcd%2FE24329_01%2Fweb.1211%2Fe24419%2F
	 * writing.htm%23LOGSV159
	 */
	private static NonCatalogLogger nc = new NonCatalogLogger(
			XMLUtils.class.getName());
	/**
	 * Tells if the traces must be written
	 */
	private boolean isDebugEnabled = false;
	
	/**
	 * Encapsulated the minimum set of attributes for a SAMLResponse 
	 */
	private SamlVO samlVO;
	
	/**
	 * Instantiates a SamlVO
	 * 
	 * @param isDebugEnabled
	 */
	public XMLhandler(boolean isDebugEnabled) {
		this.isDebugEnabled = isDebugEnabled;
		samlVO = new SamlVO();
	}

	/**
	 * Receive notification of the start of an element. Actually we will only
	 * take care about:
	 * 
	 * <ul>
	 * <li>samlp:LogoutRequest</li>
	 * <li>ID</li>
	 * <li>Destination</li>
	 * </ul>
	 * 
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		// Managing a LogoutRequest means that we are going to build a
		// LogoutResponse
		if (qName.equals("samlp:LogoutRequest")) {

			if (this.isDebugEnabled)
				nc.notice("samlp:LogoutRequest element found");

			// The ID value of a request will be the LogoutResponse's
			// InReponseTo attribute
			samlVO.setInResponseTo(attributes.getValue("ID"));
			// From the destination we can get the Issuer element
			String destination = attributes.getValue("Destination");
			if (destination != null) {
				URL destinationUrl = null;
				try {
					destinationUrl = new URL(destination);
				} catch (MalformedURLException e) {
					// This URL always must be well formed. Workaround set the
					// our IdP URL
					samlVO.setIssuer(Constants.IDP_DOMAIN);
					nc.error(e.getMessage());
				}
				samlVO.setIssuer(destinationUrl.getHost());
			}

			if (this.isDebugEnabled)
				nc.notice("samlp:LogoutRequest element parsed");
		}
	}
	
	/**
	 * Getter for samlVO
	 * @return SamlVO instance
	 */
	public SamlVO getSamlVO() {
		return samlVO;
	}

}
