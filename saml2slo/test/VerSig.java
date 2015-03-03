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
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class VerSig {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Verify a DSA signature */

		if (args.length != 3) {
			System.out.println("Usage: VerSig "
					+ "publickeyfile signaturefile " + "datafile");
		} else
			try {

				// Input and Convert the Encoded Public Key Bytes
				FileInputStream keyfis = new FileInputStream(args[0]);
				byte[] encKey = new byte[keyfis.available()];
				keyfis.read(encKey);

				keyfis.close();

				CertificateFactory certificateFactory = CertificateFactory
						.getInstance("X509");
				Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(encKey));
				PublicKey pubKey = certificate.getPublicKey();
				
				// Input the Signature Bytes
				FileInputStream sigfis = new FileInputStream(args[1]);
				byte[] sigToVerify = new byte[sigfis.available()]; 
				sigfis.read(sigToVerify);
				sigfis.close();
				
				// Initialize the Signature Object for Verification
				Signature sig = Signature.getInstance("SHA1withRSA");
				sig.initVerify(pubKey);
				
				// Supply the Signature Object With the Data to be Verified
				FileInputStream datafis = new FileInputStream(args[2]);
				BufferedInputStream bufin = new BufferedInputStream(datafis);

				byte[] buffer = new byte[1024];
				int len;
				while (bufin.available() != 0) {
				    len = bufin.read(buffer);
				    sig.update(buffer, 0, len);
				};

				bufin.close();
				
				// Verify the signature
				boolean verifies = sig.verify(sigToVerify);
				System.out.println("signature verifies: " + verifies);
				

			} catch (Exception e) {
				System.err.println("Caught exception " + e.toString());
			}

	}

}
