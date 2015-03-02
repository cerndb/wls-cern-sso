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
