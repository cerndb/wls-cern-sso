package ch.cern.sso.weblogic.principals;

import java.util.ArrayList;

import weblogic.security.spi.WLSUser;

public class CernWlsUserPrincipal extends CernWlsPrincipal implements WLSUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7991961538805280317L;

	private String upn;
	private String emailAddress;
	private String role;
	private String displayName;
	private String identityClass;
	private String phoneNumber;
	private String building;
	private String firstName;
	private String lastName;
	private String department;
	private String homeInstitute;
	private String personID;
	private String uidNumber;
	private String gidNumber;
	private String preferredLanguage;
	private ArrayList<String> groups;

	public CernWlsUserPrincipal() {
		super();
		groups = new ArrayList<String>();
	}

	public CernWlsUserPrincipal(String name) {
		super(name);
		groups = new ArrayList<String>();
	}

	public void addGroup(String group) {
		groups.add(group);
	}

	public String getUpn() {
		return upn;
	}

	public void setUpn(String upn) {
		this.upn = upn;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getHomeInstitute() {
		return homeInstitute;
	}

	public void setHomeInstitute(String homeInstitute) {
		this.homeInstitute = homeInstitute;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public String getUidNumber() {
		return uidNumber;
	}

	public void setUidNumber(String uidNumber) {
		this.uidNumber = uidNumber;
	}

	public String getGidNumber() {
		return gidNumber;
	}

	public void setGidNumber(String gidNumber) {
		this.gidNumber = gidNumber;
	}

	public void setGroups(ArrayList<String> groups) {
		this.groups = groups;
	}

	public ArrayList<String> getGroups() {
		return groups;
	}

	public String getIdentityClass() {
		return identityClass;
	}

	public void setIdentityClass(String identityClass) {
		this.identityClass = identityClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((building == null) ? 0 : building.hashCode());
		result = prime * result
				+ ((department == null) ? 0 : department.hashCode());
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((gidNumber == null) ? 0 : gidNumber.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result
				+ ((homeInstitute == null) ? 0 : homeInstitute.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((personID == null) ? 0 : personID.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result
				+ ((uidNumber == null) ? 0 : uidNumber.hashCode());
		result = prime * result + ((upn == null) ? 0 : upn.hashCode());
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
		CernWlsUserPrincipal other = (CernWlsUserPrincipal) obj;
		if (building == null) {
			if (other.building != null)
				return false;
		} else if (!building.equals(other.building))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gidNumber == null) {
			if (other.gidNumber != null)
				return false;
		} else if (!gidNumber.equals(other.gidNumber))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (homeInstitute == null) {
			if (other.homeInstitute != null)
				return false;
		} else if (!homeInstitute.equals(other.homeInstitute))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (personID == null) {
			if (other.personID != null)
				return false;
		} else if (!personID.equals(other.personID))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (uidNumber == null) {
			if (other.uidNumber != null)
				return false;
		} else if (!uidNumber.equals(other.uidNumber))
			return false;
		if (upn == null) {
			if (other.upn != null)
				return false;
		} else if (!upn.equals(other.upn))
			return false;
		return true;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}
}
