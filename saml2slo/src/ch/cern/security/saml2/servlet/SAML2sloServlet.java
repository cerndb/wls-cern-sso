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
package ch.cern.security.saml2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weblogic.logging.NonCatalogLogger;
import ch.cern.security.saml2.utils.LogoutUtils;
import ch.cern.security.saml2.utils.UrlUtils;
import conf.Constants;

public class SAML2sloServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4084247528449402000L;

	/**
	 * Log see
	 * http://www.oracle.com/pls/fmw121100/to_URL?remark=ranked&urlname=http
	 * :%2F%2Fdownload.oracle.com%2Fdocs%2Fcd%2FE24329_01%2Fweb.1211%2Fe24419%2F
	 * writing.htm%23LOGSV159
	 */
	private NonCatalogLogger nc = new NonCatalogLogger(this.getClass()
			.getName());

	private boolean isDebugEnabled = false;

	/**
	 * Processes the SAMLRequest and generates a SAMLResponse
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String redirectUrl = new String(Constants.DEFAULT_SSO_SIGNOUT_URL);
		try {
			// Start "timer"
			Date begin = new Date();
			
			// Check if the debug_slo has is in the request
			this.isDebugEnabled = LogoutUtils.checkDebugStatus(request);

			if (this.isDebugEnabled)
				nc.notice("Starting SLO");

			// Redirect Url
			redirectUrl = request.getSession().getServletContext()
					.getInitParameter(Constants.SSO_SIGNOUT_URL);

			/*
			 * Check the request parameters We need three SAMLRequest, SigAlg and
			 * Signature
			 */
			ArrayList<String> parameterNames = Collections.list(request
					.getParameterNames());

			// Check if we are managing a SAMLRequest or a SAMLResponse
			if (parameterNames.contains(Constants.SAML_REQUEST)) {
				
				if (this.isDebugEnabled)
					nc.notice(Constants.SAML_REQUEST + " is in the URL parameters");
				
				// Verify the signature
				if(!UrlUtils.verify(request, isDebugEnabled)){
					nc.error(Constants.SIGNATURE_NO_VERIFY_EXCEPTION);
					throw new Exception(Constants.SIGNATURE_NO_VERIFY_EXCEPTION);
				} else {
					if (this.isDebugEnabled)
						nc.notice(Constants.SAML_REQUEST + " signature verified!!!");
				}

				// Generate the redirectUrl with the SAMLResponse
				redirectUrl = UrlUtils.generateSamlResponse(request, this.isDebugEnabled);
				
				// Local logout
				LogoutUtils.weblogicLogout(request, this.isDebugEnabled);
			}
			
			if (this.isDebugEnabled)
				nc.notice("Finishing SLO");
			
			// Set finish "timer"
			Date end = new Date();
			
			// Calculates filter processing time
			long processingTimeInMillis = end.getTime() - begin.getTime();
			if (this.isDebugEnabled)
				nc.notice("Processing time in milliseconds: " + processingTimeInMillis);
			
			// Redirect
			response.sendRedirect(redirectUrl);
		} catch (Exception e) {
			nc.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
