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
package conf;

public class Constants {
	
	public static final String SP_PRIVATE_KEY = "privateKey";
	public static final String SERVLET_CONTEXT_NULL_EXCEPTION = "Servlet Context is null!!!";
	public static final String SAML_REQUEST = "SAMLRequest";
	public static final String SAML_RESPONSE = "SAMLResponse";
	public static final String USER_NAME_PARAMETER = "userName";
	public static final String SSO_SIGNOUT_URL = "ssoSignOutUrl";
	public static final String CHARACTER_ENCODING = "ISO-8859-1";
	public static final String ENTITY_ID = "EntityID";
	public static final String SSO_SIGNING_KEY_ALIAS = "SSOSigningKeyAlias";
	public static final String SP_PRIVATE_KEY_EXCEPTION = "SSO SP Private key not found in the server configuration!!!";
	public static final String ENTITY_ID_EXCEPTION = "EntityID field not found in Federation Services configuration!!!";
	public static final String IDP_PUBLIC_KEY_EXCEPTION = "SSO IDP Public key not found in the server configuration!!!";
	public static final String IDP_PUBLIC_KEY = "publicKey";
	public static final String DEBUG_SLO = "debug_slo";
	public static final String IDP_DOMAIN = "https://login.cern.ch";
	public static final String SIG_ALG = "SigAlg";
	public static final String SIG_ALG_EXCEPTION = "Signature Algorithm is not provided as context param!!!";
	public static final String ALGORITHM = "algorithm";
	public static final String IDP_ENDPOINT = "idpEndpoint";
	public static final String ALGORITHM_EXCEPTION = "Algorithm is not provided as context param!!!";
	public static final String IDP_ENDPOINT_EXCEPTION = "IdP Endpoint is not provided as context param!!!";
	public static final String SIGNATURE_NO_VERIFY_EXCEPTION = "Signature no verified!!!";
	public static final String DEFAULT_SSO_SIGNOUT_URL = "https://login.cern.ch/adfs/ls/?wa=wsignout1.0";
}
