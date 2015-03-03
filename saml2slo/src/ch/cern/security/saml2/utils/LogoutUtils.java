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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import weblogic.logging.NonCatalogLogger;
import weblogic.servlet.security.ServletAuthentication;
import conf.Constants;

public class LogoutUtils {

	/**
	 * Log see
	 * http://www.oracle.com/pls/fmw121100/to_URL?remark=ranked&urlname=http
	 * :%2F%2Fdownload.oracle.com%2Fdocs%2Fcd%2FE24329_01%2Fweb.1211%2Fe24419%2F
	 * writing.htm%23LOGSV159
	 */
	private static NonCatalogLogger nc = new NonCatalogLogger(
			LogoutUtils.class.getName());

	/**
	 * Standar way: invalidateSession. But keep in mind: "the user's
	 * authentication information still remains valid and is stored in the
	 * context of the server or virtual host" See http://download.oracle.com/
	 * docs/cd/E17904_01/web.1111/e13712/sessions.htm#WBAPP300 The servlet
	 * specification does not provide an API for logging
	 * 
	 * @param request
	 * @param isDebugEnabled
	 * @throws ServletException
	 */
	public static void weblogicLogout(HttpServletRequest request,
			boolean isDebugEnabled) throws ServletException {

		// Get user info. Debugging purposes
		String userName = null;
		if (isDebugEnabled) {
			try {
				userName = request.getUserPrincipal().getName();
				nc.notice("Logging out user: " + userName);
			} catch (Exception e) {
				nc.error(e.getMessage());
			}
		}

		// Invalidates all the sessions for the current user only (that is, the
		// current cookie), and since the cookie is no longer required, kills
		// the cookie too.
		ServletAuthentication.invalidateAll(request);

		// Kills the current cookie.
		ServletAuthentication.killCookie(request);

		if (isDebugEnabled) {
			if (userName!=null){
				nc.notice(userName + " has been logging out");
			} else {
				nc.notice("The session has been already killed: cookies deletion/expiration");
			}
		}
	}

	/**
	 * Looks for the conf.Constants.DEBUG_SLO cookie. This cookie tells the
	 * application to write or not in the .out
	 * 
	 * @param request
	 * @return true or false
	 */
	public static boolean checkDebugStatus(HttpServletRequest request) {

		boolean debugStatus = false;

		// We will look in the COOKIES for the debug parameter
		Cookie[] cookies = ((HttpServletRequest) request).getCookies();
		// Check the cookies
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(Constants.DEBUG_SLO)) {
					debugStatus = Boolean.valueOf((cookies[i].getValue()));
				}
			}
		} else {
			nc.notice("The client is not sending any COOKIE");
		}

		return debugStatus;
	}

	public static char[] urlEncode(String data, String characterEncoding)
			throws UnsupportedEncodingException {
		char[] temp = URLEncoder.encode(data, characterEncoding).toCharArray();
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] == '%') {
				temp[i + 1] = Character.toLowerCase(temp[i + 1]);
				temp[i + 2] = Character.toLowerCase(temp[i + 2]);
			}
		}
		return temp;
	}

}
