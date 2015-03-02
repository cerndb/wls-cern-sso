package ch.cern.sso.weblogic.mappers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import weblogic.security.service.ContextHandler;
import ch.cern.sso.weblogic.mappers.attributes.Attribute;
import ch.cern.sso.weblogic.principals.CernWlsUserPrincipal;

import com.bea.security.saml2.providers.SAML2AttributeInfo;
import com.bea.security.saml2.providers.SAML2AttributeStatementInfo;
import com.bea.security.saml2.providers.SAML2IdentityAsserterAttributeMapper;
import com.bea.security.saml2.providers.SAML2IdentityAsserterNameMapper;
import com.bea.security.saml2.providers.SAML2NameMapperInfo;

public class CernWlsUserPrincipalMapper implements
		SAML2IdentityAsserterAttributeMapper, SAML2IdentityAsserterNameMapper {

	public CernWlsUserPrincipalMapper() {
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

		CernWlsUserPrincipal cernWlsUserPrincipal = new CernWlsUserPrincipal();

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

		principals.add(cernWlsUserPrincipal);

		return principals;
	}

	@Override
	public String mapNameInfo(SAML2NameMapperInfo mapperInfo,
			ContextHandler contextHandler) {
		return mapperInfo.getName();
	}
}
