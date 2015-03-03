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

import javax.servlet.http.HttpServletRequest;

public class OpenSAMLxmlUtils {

	public static void parseLogoutRequest(HttpServletRequest request) {
		
//		DefaultBootstrap.bootstrap();
//
//		HTTPRedirectDeflateDecoder decode = new HTTPRedirectDeflateDecoder(
//				new BasicParserPool());
//		BasicSAMLMessageContext<LogoutRequest, ?, ?> messageContext = new BasicSAMLMessageContext<LogoutRequest, SAMLObject, SAMLObject>();
//		messageContext
//				.setInboundMessageTransport(new HttpServletRequestAdapter(
//						request));
//		decode.decode(messageContext);
//
//		XMLObjectBuilderFactory builderFactory = org.opensaml.Configuration
//				.getBuilderFactory();
//		LogoutRequestBuilder logoutRequestBuilder = (LogoutRequestBuilder) builderFactory
//				.getBuilder(LogoutRequest.DEFAULT_ELEMENT_NAME);
//		LogoutRequest logoutRequest = logoutRequestBuilder.buildObject();
//		logoutRequest = (LogoutRequest) messageContext.getInboundMessage();
	}
}
