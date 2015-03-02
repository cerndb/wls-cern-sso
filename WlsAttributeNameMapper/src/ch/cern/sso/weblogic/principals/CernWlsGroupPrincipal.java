/*******************************************************************************
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "COPYING".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 *
 *
 *******************************************************************************/
package ch.cern.sso.weblogic.principals;

import weblogic.security.spi.WLSGroup;

public class CernWlsGroupPrincipal extends CernWlsPrincipal implements WLSGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5685387360713237532L;

	public CernWlsGroupPrincipal() {
		super();
	}

	public CernWlsGroupPrincipal(String name) {
		super(name);
	}
	
}
