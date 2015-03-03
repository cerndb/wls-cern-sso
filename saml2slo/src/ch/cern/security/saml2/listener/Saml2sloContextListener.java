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
package ch.cern.security.saml2.listener;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import weblogic.logging.NonCatalogLogger;
import ch.cern.security.saml2.jmx.JmxConstants;
import ch.cern.security.saml2.jmx.JmxUtils;
import ch.cern.security.saml2.utils.ScriptUtils;
import conf.Constants;

public class Saml2sloContextListener implements ServletContextListener {

	/**
	 * Log see
	 * http://www.oracle.com/pls/fmw121100/to_URL?remark=ranked&urlname=http
	 * :%2F%2Fdownload.oracle.com%2Fdocs%2Fcd%2FE24329_01%2Fweb.1211%2Fe24419%2F
	 * writing.htm%23LOGSV159
	 */
	private NonCatalogLogger nc = new NonCatalogLogger(this.getClass()
			.getName());

	/**
	 * 
	 */
	private static final String KEYSTORE_TYPE = "keystoreType";
	private static final String KEYSTORE_PROVIDER = "keystoreProvider";
	private static final String CUSTOM_IDENTITY_KEYSTORE_FILE_NAME = "CustomIdentityKeyStoreFileName";
	private static final String KEYSTORE_PASSWORD = "keystorePasswordKey";
	private static final String PRIVATE_KEY_PASSWORD = "privateKeyPasswordKey";
	private static final String GET_PASSWD_SCRIPT = "getPasswdScript";
	private static final String AUTHENTICATOR_PROVIDER_NAME = "authenticationProviderName";
	private static final String WEB_SSO_PARTNER_NAME = "webSSOpartnerName";
	private static final String PASSWORD_WEBLOGIC = "password_weblogic_";

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}

	/**
	 * 
	 * @param servletContextEvent
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		try {

			// Get the ServletContext
			ServletContext servletContext = servletContextEvent
					.getServletContext();

			/*
			 * If ServletContext is null the deployment must be stopped TODO: we
			 * are not getting the error in the console or set the status as
			 * failed
			 */
			if (servletContext == null) {
				throw new RuntimeException(
						Constants.SERVLET_CONTEXT_NULL_EXCEPTION);
			}

			// Get the SP SSO private key
			PrivateKey privateKey = getPrivateKey(servletContext);
			// Store the SP SSO private key in the servlet context
			if (privateKey != null) {
				nc.notice("SP private key got");
				servletContextEvent.getServletContext().setAttribute(
						Constants.SP_PRIVATE_KEY, privateKey);
			} else {
				throw new RuntimeException(Constants.SP_PRIVATE_KEY_EXCEPTION);
			}

			// Get the IdP SSO public key
			PublicKey publicKey = getIdpPublicKey(servletContext);
			if (publicKey != null) {
				nc.notice("IdP public key got");
				servletContextEvent.getServletContext().setAttribute(
						Constants.IDP_PUBLIC_KEY, publicKey);
			} else {
				throw new RuntimeException(Constants.IDP_PUBLIC_KEY_EXCEPTION);
			}

			// Get the sigAlg
			String sigAlg = servletContext.getInitParameter(Constants.SIG_ALG);
			// Store the sigAlg in the servlet context
			if (sigAlg != null) {
				nc.notice(sigAlg);
				servletContextEvent.getServletContext().setAttribute(
						Constants.SIG_ALG, sigAlg);
			} else {
				throw new RuntimeException(Constants.SIG_ALG_EXCEPTION);
			}

			// Get the algorithm entityID
			String algorithm = servletContext
					.getInitParameter(Constants.ALGORITHM);
			// Store the algorithm in the servlet context
			if (algorithm != null) {
				nc.notice(algorithm);
				servletContextEvent.getServletContext().setAttribute(
						Constants.ALGORITHM, algorithm);
			} else {
				throw new RuntimeException(Constants.ALGORITHM_EXCEPTION);
			}

			// Get the idpEndpoint
			String idpEndpoint = servletContext
					.getInitParameter(Constants.IDP_ENDPOINT);
			// Store the idpEndpoint in the servlet context
			if (idpEndpoint != null) {
				nc.notice(idpEndpoint);
				servletContextEvent.getServletContext().setAttribute(
						Constants.IDP_ENDPOINT, idpEndpoint);
			} else {
				throw new RuntimeException(Constants.IDP_ENDPOINT_EXCEPTION);
			}

			// Get the SP entityID
			String entityID = getEntityID();
			// Store the entityID in the servlet context
			if (entityID != null) {
				nc.notice(entityID);
				servletContextEvent.getServletContext().setAttribute(
						Constants.ENTITY_ID, entityID);
			} else {
				throw new RuntimeException(Constants.ENTITY_ID_EXCEPTION);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private PublicKey getIdpPublicKey(ServletContext servletContext)
			throws NamingException, MalformedObjectNameException,
			AttributeNotFoundException, InstanceNotFoundException,
			MBeanException, ReflectionException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchProviderException,
			CertificateException, IOException {
		/*
		 * Retrieve the public key using the Weblogic Server configuration
		 * (through JMX) and store it in the ServletContext
		 */

		// Get the Authenticator Provider Name
		String authenticationProviderName = servletContext
				.getInitParameter(AUTHENTICATOR_PROVIDER_NAME);

		// Get the Web SSO IdP partner name
		String webSSOpartnerName = servletContext
				.getInitParameter(WEB_SSO_PARTNER_NAME);

		String credentials = ScriptUtils.getValueFromScript(
				servletContext.getInitParameter(GET_PASSWD_SCRIPT),
				PASSWORD_WEBLOGIC + JmxUtils.getDomainValue(JmxConstants.NAME));

		// Get the encoded byte[] of the key
		byte[] encodedBytes = (byte[]) JmxUtils.getCertificate(
				authenticationProviderName, webSSOpartnerName,
				JmxConstants.SECURITY_PRINCIPAL, credentials);

		// Create the certificate
		CertificateFactory certificateFactory = CertificateFactory
				.getInstance("X509");
		Certificate certificate = certificateFactory
				.generateCertificate(new ByteArrayInputStream(encodedBytes));
		
		// Get the public key
		return certificate.getPublicKey();
	}

	private String getEntityID() throws NamingException,
			MalformedObjectNameException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException {
		// Retrieve the entityID attribute (The string that uniquely
		// identifies the local site) from the Federation Services
		// configuration of the server
		String entityID = (String) JmxUtils.getValue(
				JmxConstants.RUNTIME_SERVICE,
				JmxConstants.SINGLE_SIGN_ON_SERVICES, Constants.ENTITY_ID,
				JmxConstants.RUNTIME_SERVICE_MBEAN);
		return entityID;
	}

	private PrivateKey getPrivateKey(ServletContext servletContext)
			throws IOException, KeyStoreException, NoSuchProviderException,
			FileNotFoundException, NamingException,
			MalformedObjectNameException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException,
			NoSuchAlgorithmException, CertificateException,
			UnrecoverableKeyException {
		/*
		 * Retrieve the private key using the Weblogic Server configuration
		 * (through JMX) and store it in the ServletContext
		 */

		// Get keystore password (jks file)
		String keyStorePassword = ScriptUtils.getValueFromScript(
				servletContext.getInitParameter(GET_PASSWD_SCRIPT),
				servletContext.getInitParameter(KEYSTORE_PASSWORD));

		// Get key password (alias sso-...)
		String privateKeyPassword = ScriptUtils.getValueFromScript(
				servletContext.getInitParameter(GET_PASSWD_SCRIPT),
				servletContext.getInitParameter(PRIVATE_KEY_PASSWORD));

		// Instantiate a keystore object
		KeyStore keyStore = KeyStore.getInstance(
				servletContext.getInitParameter(KEYSTORE_TYPE),
				servletContext.getInitParameter(KEYSTORE_PROVIDER));

		// Load the keystore (open file and load bytes into keystore object)
		FileInputStream stream = new FileInputStream(
				JmxUtils.getValue(CUSTOM_IDENTITY_KEYSTORE_FILE_NAME));
		keyStore.load(stream, keyStorePassword.toCharArray());

		// Get the key (through its alias) from the keystore
		PrivateKey privateKey = (PrivateKey) keyStore.getKey((String) JmxUtils
				.getValue(JmxConstants.RUNTIME_SERVICE,
						JmxConstants.SINGLE_SIGN_ON_SERVICES,
						Constants.SSO_SIGNING_KEY_ALIAS,
						JmxConstants.RUNTIME_SERVICE_MBEAN), privateKeyPassword
				.toCharArray());

		return privateKey;
	}

}
