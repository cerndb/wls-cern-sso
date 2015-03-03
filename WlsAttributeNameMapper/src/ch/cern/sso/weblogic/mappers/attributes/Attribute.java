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
package ch.cern.sso.weblogic.mappers.attributes;

import java.util.Collection;

import ch.cern.sso.weblogic.principals.CernWlsGroupPrincipal;
import ch.cern.sso.weblogic.principals.CernWlsPrincipal;
import ch.cern.sso.weblogic.principals.CernWlsUserPrincipal;

public enum Attribute {

	UPN("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/upn") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setUpn((String) value);
		}
	},
	EMAIL_ADDRESS("http://schemas.xmlsoap.org/claims/EmailAddress") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setEmailAddress((String) value);
		}
	},
	COMMON_NAME("http://schemas.xmlsoap.org/claims/CommonName") {
		public void setValue(Object principal, Object value) {
			((CernWlsPrincipal) principal).setCommonName((String) value);
		}
	},
	ROLE("http://schemas.microsoft.com/ws/2008/06/identity/claims/role") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setRole((String) value);
		}
	},
	IDENTITY_CLASS("http://schemas.xmlsoap.org/claims/IdentityClass") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setIdentityClass((String) value);
		}
	},
	DISPLAY_NAME("http://schemas.xmlsoap.org/claims/DisplayName") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setDisplayName((String) value);
		}
	},
	PHONE_NUMBER("http://schemas.xmlsoap.org/claims/PhoneNumber") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setPhoneNumber((String) value);
		}
	},
	BUILDING("http://schemas.xmlsoap.org/claims/Building") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setBuilding((String) value);
		}
	},
	FIRST_NAME("http://schemas.xmlsoap.org/claims/Firstname") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setFirstName((String) value);
		}
	},
	LAST_NAME("http://schemas.xmlsoap.org/claims/Lastname") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setLastName((String) value);
		}
	},
	DEPARTMENT("http://schemas.xmlsoap.org/claims/Department") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setDepartment((String) value);
		}
	},
	HOME_INSTITUTE("http://schemas.xmlsoap.org/claims/HomeInstitute") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setHomeInstitute((String) value);
		}
	},
	PERSON_ID("http://schemas.xmlsoap.org/claims/PersonID") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setPersonID((String) value);
		}
	},
	UID_NUMBER("http://schemas.xmlsoap.org/claims/uidNumber") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setUidNumber((String) value);
		}
	},
	GID_NUMBER("http://schemas.xmlsoap.org/claims/gidNumber") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setGidNumber((String) value);
		}
	},
	PREFERRED_LANGUAGE("http://schemas.xmlsoap.org/claims/PreferredLanguage") {
		public void setValue(Object principal, Object value) {
			((CernWlsUserPrincipal) principal).setPreferredLanguage((String) value);
		}
	},
	GROUP("http://schemas.xmlsoap.org/claims/Group") {
		public void setValue(Object principal, Object value) {
			if (principal instanceof CernWlsUserPrincipal) {
				Collection<String> groups = (Collection<String>) value;
				if (groups != null && groups.size() > 0) {
					for (String groupName : groups) {
						((CernWlsUserPrincipal) principal).addGroup(groupName);
					}
				}
			}
			if (principal instanceof CernWlsGroupPrincipal) {
				((CernWlsGroupPrincipal) principal)
						.setCommonName((String) value);
			}
		}
	};

	private final String name;

	private Attribute(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public abstract void setValue(Object principal, Object value);
}
