/*******************************************************************************
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 *
 *
 *******************************************************************************/
package ch.cern.sso.weblogic.mappers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import weblogic.security.service.ContextHandler;
import ch.cern.sso.weblogic.mappers.attributes.Attribute;
import ch.cern.sso.weblogic.principals.CernWlsGroupPrincipal;
import ch.cern.sso.weblogic.principals.CernWlsPrincipal;
import ch.cern.sso.weblogic.principals.CernWlsUserPrincipal;

import com.bea.security.saml2.providers.SAML2AttributeInfo;
import com.bea.security.saml2.providers.SAML2AttributeStatementInfo;
import com.bea.security.saml2.providers.SAML2IdentityAsserterAttributeMapper;
import com.bea.security.saml2.providers.SAML2IdentityAsserterNameMapper;
import com.bea.security.saml2.providers.SAML2NameMapperInfo;

public class CernWlsPrincipalMapper implements
		SAML2IdentityAsserterAttributeMapper, SAML2IdentityAsserterNameMapper {

	public CernWlsPrincipalMapper() {
		super();
	}

	@Override
	public Collection<Principal> mapAttributeInfo(
			Collection<SAML2AttributeStatementInfo> attrStmtInfos,
			ContextHandler contextHandler) {

		if (attrStmtInfos == null || attrStmtInfos.size() == 0) {
			return null;
		}

		Collection<Principal> principals = new ArrayList<Principal>();

		CernWlsPrincipal cernWlsUserPrincipal = new CernWlsUserPrincipal();

		for (SAML2AttributeStatementInfo stmtInfo : attrStmtInfos) {
			Collection<SAML2AttributeInfo> attrs = stmtInfo.getAttributeInfo();
			if (attrs == null || attrs.size() == 0) {
				System.out
						.println(this.getClass().getCanonicalName()
								+ ": no attribute in statement: "
								+ stmtInfo.toString());
			} else {
				for (SAML2AttributeInfo attr : attrs) {
					for (Attribute attribute : Attribute.values()) {
						// Check if we want to create a group principal
						if (attribute.getName().equals(Attribute.GROUP.getName()) && attr.getAttributeName().equals(Attribute.GROUP.getName())) {
							for (Iterator iterator = attr.getAttributeValues()
									.iterator(); iterator.hasNext();) {
								String attributeValue = (String) iterator
										.next();
								CernWlsGroupPrincipal cernWlsGroupPrincipal = new CernWlsGroupPrincipal();
								attribute.setValue(cernWlsGroupPrincipal,
										attributeValue);
								principals.add(cernWlsGroupPrincipal);
							}
							break;
						} else {
							if (attr.getAttributeName().equals(attribute.getName())) {
								if(attr.getAttributeValues().size()>1){
									attribute.setValue(cernWlsUserPrincipal, attr
											.getAttributeValues());
								} else {
									attribute.setValue(cernWlsUserPrincipal, attr
											.getAttributeValues().iterator().next());
								}
								break;
							}
						}
					}
				}
			}
		}

		principals.add(cernWlsUserPrincipal);

		return principals;
	}

	@Override
	public String mapNameInfo(SAML2NameMapperInfo mapperInfo,
			ContextHandler contextHandler) {
		return mapperInfo.getName();
	}
}
