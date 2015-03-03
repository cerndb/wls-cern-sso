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
package ch.cern.security.saml2.vo;

/**
 * Encapsulates the minimum necessary elements of a <samlp:LogoutRequest>/<samlp:LogoutResponse>
 * 
 * @author Luis Rodriguez Fernandez lurodrig@cern.ch
 * 
 */
public class SamlVO {

	/**
	 * i.e ID="_695df506300de2973485109aba537176"
	 */
	private String id;

	/**
	 * i.e. IssueInstant="2012-06-04T16:52:11.768+0200"
	 */
	private String issueInstant;

	/**
	 * i.e. <Issuer
	 * xmlns="urn:oasis:names:tc:SAML:2.0:assertion">https://cern.ch
	 * /login</Issuer>
	 */
	private String issuer;

	/**
	 * <NameID Format="urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress"
	 * xmlns
	 * ="urn:oasis:names:tc:SAML:2.0:assertion">luis.rodriguez.fernandez@cern.
	 * ch</NameID>
	 */
	private String nameId;

	/**
	 * InResponseTo="_6c5bd167-46bd-417c-861c-4df6dad86295"
	 */
	private String inResponseTo;
	
	/**
	 * Destination="https://opendayslogin.cern.ch/adfs/ls/"
	 */
	private String destination;
	
	/**
	 * Void constructor
	 */
	public SamlVO() {}

	public SamlVO(String id, String issueInstant, String issuer, String nameId) {
		super();
		this.id = id;
		this.issueInstant = issueInstant;
		this.issuer = issuer;
		this.nameId = nameId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIssueInstant() {
		return issueInstant;
	}

	public void setIssueInstant(String issueInstant) {
		this.issueInstant = issueInstant;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getInResponseTo() {
		return inResponseTo;
	}

	public void setInResponseTo(String inResponseTo) {
		this.inResponseTo = inResponseTo;
	}

	@Override
	public String toString() {
		return "SamlVO [id=" + id + ", issueInstant=" + issueInstant
				+ ", issuer=" + issuer + ", nameId=" + nameId
				+ ", inResponseTo=" + inResponseTo + "]";
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}
