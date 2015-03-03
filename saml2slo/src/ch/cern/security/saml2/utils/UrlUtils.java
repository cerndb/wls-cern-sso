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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.xml.sax.SAXException;

import weblogic.logging.NonCatalogLogger;

import ch.cern.security.saml2.utils.signature.SignatureUtils;
import ch.cern.security.saml2.utils.xml.XMLUtils;
import ch.cern.security.saml2.vo.SamlVO;
import conf.Constants;

/**
 * 
 * @author Luis Rodriguez Fernandez lurodrig@cern.ch
 * 
 */
public class UrlUtils {

	/**
	 * Log see
	 * http://www.oracle.com/pls/fmw121100/to_URL?remark=ranked&urlname=http
	 * :%2F%2Fdownload.oracle.com%2Fdocs%2Fcd%2FE24329_01%2Fweb.1211%2Fe24419%2F
	 * writing.htm%23LOGSV159
	 */
	private static NonCatalogLogger nc = new NonCatalogLogger(
			UrlUtils.class.getName());

	private static final String AMPERSAND = "&";
	private static final String QUESTION_MARK = "?";
	private static final String EQUAL = "=";
	private static final String SIGNATURE = "Signature";
	private static final String RANDOM_ALGORITHM = "SHA1PRNG";
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	/**
	 * Generates the URL response (IdP)
	 * 
	 * @param request
	 * @param isDebugEnabled
	 * @return The logout URL:
	 *         https://login.cern.ch/adfs/ls/?SAMLResponse=value&
	 *         SigAlg=value&Signature=value
	 * @throws DataFormatException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 * @throws InvalidKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws SignatureException
	 * @throws XMLStreamException
	 */
	public static String generateSamlResponse(HttpServletRequest request,
			boolean isDebugEnabled) throws DataFormatException,
			ParserConfigurationException, SAXException, IOException,
			UnrecoverableKeyException, InvalidKeyException, KeyStoreException,
			NoSuchProviderException, NoSuchAlgorithmException,
			CertificateException, SignatureException, XMLStreamException {

		// Get the request as an XML
		String xmlLogoutRequest = XMLUtils.xmlDecodeAndInflate(
				request.getParameter(Constants.SAML_REQUEST), isDebugEnabled);

		// Parse the xml request. Encapsulate the data in a SamlVO object
		SamlVO samlVO = XMLUtils.parseXMLmessage(xmlLogoutRequest,
				isDebugEnabled);
		
		// Add the destination: context-param in web.xml. This info is not in the logoutRequest
		samlVO.setDestination((String) request.getSession().getServletContext()
		.getAttribute(Constants.IDP_ENDPOINT));
		
		// Generates the ID
		byte[] buf = new byte[16];
		SecureRandom.getInstance(RANDOM_ALGORITHM).nextBytes(buf);
		samlVO.setId("_".concat(new String(Hex.encode(buf))));

		// Set the issueInstant
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		samlVO.setIssueInstant(simpleDateFormat.format(new Date()));

		// Get the entityID!!!
		samlVO.setIssuer((String) request.getSession().getServletContext()
				.getAttribute(Constants.ENTITY_ID));

		// Generate the LogoutResponse
		String samlResponse = XMLUtils
				.createXMLresponse(samlVO, isDebugEnabled);

		// Deflate and encode the LogoutResponse
		String base64response = XMLUtils.xmlDeflateAndEncode(samlResponse,
				isDebugEnabled);

		// URL-encode the deflatedResponse
		String urlEncodedresponse = URLEncoder.encode(base64response,
				Constants.CHARACTER_ENCODING);

		if (isDebugEnabled)
			nc.notice("Data to sign: "
					+ Constants.SAML_RESPONSE
					+ EQUAL
					+ urlEncodedresponse
					+ AMPERSAND
					+ Constants.SIG_ALG
					+ EQUAL
					+ URLEncoder.encode((String) request.getSession()
							.getServletContext()
							.getAttribute(Constants.SIG_ALG),
							Constants.CHARACTER_ENCODING));

		// Sign the SAMLResponse=value&SigAlg=value
		String signature = SignatureUtils.sign(
				Constants.SAML_RESPONSE
						+ EQUAL
						+ urlEncodedresponse
						+ AMPERSAND
						+ Constants.SIG_ALG
						+ EQUAL
						+ URLEncoder.encode(
								(String) request.getSession()
										.getServletContext()
										.getAttribute(Constants.SIG_ALG),
								Constants.CHARACTER_ENCODING),
				(PrivateKey) request.getSession().getServletContext()
						.getAttribute(Constants.SP_PRIVATE_KEY),
				(String) request.getSession().getServletContext()
						.getAttribute(Constants.ALGORITHM), isDebugEnabled);

		// URL-encode the signature
		String urlEncodedSignature = URLEncoder.encode(signature,
				Constants.CHARACTER_ENCODING);
		
		if(isDebugEnabled)
			nc.notice("Final URL: " + (String) request.getSession().getServletContext()
					.getAttribute(Constants.IDP_ENDPOINT)
					+ QUESTION_MARK
					+ Constants.SAML_RESPONSE
					+ EQUAL
					+ urlEncodedresponse
					+ AMPERSAND
					+ Constants.SIG_ALG
					+ EQUAL
					+ URLEncoder.encode((String) request.getSession()
							.getServletContext().getAttribute(Constants.SIG_ALG),
							Constants.CHARACTER_ENCODING)
					+ AMPERSAND
					+ SIGNATURE
					+ EQUAL + urlEncodedSignature);

		// Constructs the final URL
		// https://endpoint/?SAMLResponse=value&SigAlg=value&Signature=value
		return (String) request.getSession().getServletContext()
				.getAttribute(Constants.IDP_ENDPOINT)
				+ QUESTION_MARK
				+ Constants.SAML_RESPONSE
				+ EQUAL
				+ urlEncodedresponse
				+ AMPERSAND
				+ Constants.SIG_ALG
				+ EQUAL
				+ URLEncoder.encode((String) request.getSession()
						.getServletContext().getAttribute(Constants.SIG_ALG),
						Constants.CHARACTER_ENCODING)
				+ AMPERSAND
				+ SIGNATURE
				+ EQUAL + urlEncodedSignature;
	}

	/**
	 * Preprocess the request and invokes the verification:
	 * 
	 * @param request
	 * @param isDebugEnabled
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(HttpServletRequest request,
			boolean isDebugEnabled) throws Exception {

		StringBuffer data = new StringBuffer();
		String sigAlg = null;
		String signature = null;
		data.append(Constants.SAML_REQUEST);
		data.append(EQUAL);

		if (isDebugEnabled)
			nc.notice("Query String: " + request.getQueryString());

		if (request.getParameter(Constants.SAML_REQUEST) != null) {
			if (isDebugEnabled)
				nc.notice(request.getParameter(Constants.SAML_REQUEST));
			data.append(LogoutUtils.urlEncode(
					request.getParameter(Constants.SAML_REQUEST),
					Constants.CHARACTER_ENCODING));
		} else {
			nc.error("NO " + Constants.SAML_REQUEST + " parameter");
			throw new Exception("NO " + Constants.SAML_REQUEST + " parameter");
		}

		// Get the sigAlg, it should match with the one declared as context
		// param
		sigAlg = request.getParameter(Constants.SIG_ALG);
		if (isDebugEnabled)
			nc.notice(sigAlg);
		if (((String) request.getSession().getServletContext()
				.getAttribute(Constants.SIG_ALG)).equals(sigAlg)) {
			data.append(AMPERSAND);
			data.append(Constants.SIG_ALG);
			data.append(EQUAL);
			data.append(LogoutUtils.urlEncode(sigAlg,
					Constants.CHARACTER_ENCODING));
		} else {
			throw new NoSuchAlgorithmException();
		}

		// Get the signature and decode it in Base64
		Base64 base64 = new Base64();
		if (request.getParameter(SIGNATURE) != null) {
			if (isDebugEnabled)
				nc.notice("Signature: " + request.getParameter(SIGNATURE));
			signature = request.getParameter(SIGNATURE);
		} else {
			nc.error("NO " + SIGNATURE + " parameter");
			throw new Exception("NO " + SIGNATURE + " parameter");
		}

		if (isDebugEnabled)
			nc.notice("Data to verify: " + data.toString());

		// Verify
		return SignatureUtils.verify(data.toString().getBytes(),
				(byte[]) base64.decode(signature.getBytes()),
				(PublicKey) request.getSession().getServletContext()
						.getAttribute(Constants.IDP_PUBLIC_KEY));
	}

}
