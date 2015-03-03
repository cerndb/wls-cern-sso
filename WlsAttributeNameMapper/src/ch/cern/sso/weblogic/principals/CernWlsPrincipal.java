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
package ch.cern.sso.weblogic.principals;

import weblogic.security.principal.WLSAbstractPrincipal;

public class CernWlsPrincipal extends WLSAbstractPrincipal  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5500495491990106055L;
	

	private String commonName;
	
	public CernWlsPrincipal(String name) {
		super();
		// Feed the WLSAbstractPrincipal.name. Mandatory
		this.setName(name);
		this.setCommonName(name);
	}

	public CernWlsPrincipal() {
		super();
	}
	
	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		// Feed the WLSAbstractPrincipal.name. Mandatory
		super.setName(commonName);
		this.commonName = commonName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((commonName == null) ? 0 : commonName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CernWlsPrincipal other = (CernWlsPrincipal) obj;
		if (commonName == null) {
			if (other.commonName != null)
				return false;
		} else if (!commonName.equals(other.commonName))
			return false;
		return true;
	}

}
