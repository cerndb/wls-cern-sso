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
package ch.cern.security.saml2.utils.signature;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import weblogic.logging.NonCatalogLogger;
import weblogic.utils.encoders.BASE64Encoder;

/**
 * 
 * @author Luis Rodriguez Fernandez lurodrig@cern.ch
 *
 */
public class SignatureUtils{
	
	/**
	 * Log see
	 * http://www.oracle.com/pls/fmw121100/to_URL?remark=ranked&urlname=http
	 * :%2F%2Fdownload.oracle.com%2Fdocs%2Fcd%2FE24329_01%2Fweb.1211%2Fe24419%2F
	 * writing.htm%23LOGSV159
	 */
	private static NonCatalogLogger nc = new NonCatalogLogger(SignatureUtils.class.getName());
	
	/**
	 * Sign the given value with the given privateKey
	 * 
	 * @param value String value to sign
	 * @param privateKey SP private key (SSO)
	 * @param sigAlg 
	 * @param isDebugEnabled 
	 * @return The signature bytes
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static String sign(String value, PrivateKey privateKey, String sigAlg, boolean isDebugEnabled) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		
		if(isDebugEnabled)
			nc.notice(value);
			
		// Create the signature
		Signature signature = Signature.getInstance(sigAlg);
		signature.initSign(privateKey);
		signature.update(value.getBytes());
		byte[] signatureBytes = signature.sign();
		
		String signatureString = (new BASE64Encoder()).encodeBuffer(signatureBytes);
		
		if(isDebugEnabled)
			nc.notice(signatureString);
		
		return signatureString;
	}
	
	public static boolean verify(byte[] data, byte[] signature, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		
		Signature sig = Signature.getInstance("SHA1withRSA");
		sig.initVerify(publicKey);
		sig.update(data);
		boolean verifies = sig.verify(signature);
		return verifies;
	}

}
