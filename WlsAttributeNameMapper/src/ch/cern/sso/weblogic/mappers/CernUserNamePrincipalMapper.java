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
package ch.cern.sso.weblogic.mappers;

import ch.cern.sso.weblogic.mappers.attributes.Attribute;
import ch.cern.sso.weblogic.principals.CernWlsUserPrincipal;
import com.bea.security.saml2.providers.SAML2AttributeInfo;
import com.bea.security.saml2.providers.SAML2AttributeStatementInfo;
import com.bea.security.saml2.providers.SAML2IdentityAsserterAttributeMapper;
import com.bea.security.saml2.providers.SAML2IdentityAsserterNameMapper;
import com.bea.security.saml2.providers.SAML2NameMapperInfo;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import weblogic.security.service.ContextHandler;

public class CernUserNamePrincipalMapper implements
        SAML2IdentityAsserterAttributeMapper, SAML2IdentityAsserterNameMapper {
    
    private static final String ATTRIBUTE_PRINCIPALS = "com.bea.contextelement.saml.AttributePrincipals";
    
    public CernUserNamePrincipalMapper() {
        super();
    }

    @Override
    public Collection<Principal> mapAttributeInfo(
            Collection<SAML2AttributeStatementInfo> attrStmtInfos,
            ContextHandler contextHandler) {

        Collection<Principal> principals = new ArrayList<>();
        CernWlsUserPrincipal cernWlsUserPrincipal = new CernWlsUserPrincipal();

        if (attrStmtInfos == null || attrStmtInfos.isEmpty()) {
            return null;
        }

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
                            if (attr.getAttributeValues().size() > 1) {
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
        // Returns the default. For CERN SSO is the email adress.
        // For instance  <NameID Format="urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress">luis.rodriguez.fernandez@cern.ch</NameID>
        String userName = mapperInfo.getName();
        String names[] = contextHandler.getNames();
        for (String name : names) {
            if(name!=null && name.equals(ATTRIBUTE_PRINCIPALS)){
                ArrayList<CernWlsUserPrincipal> cernWlsUserPrincipals = (ArrayList<CernWlsUserPrincipal>)contextHandler.getValue(ATTRIBUTE_PRINCIPALS);
                if(!cernWlsUserPrincipals.isEmpty()){
                    // This mapper implementation creates only one principal
                    userName = cernWlsUserPrincipals.get(0).getName();
                    break;
                }
                    
            }
        }
        return userName;
    }
}
