# wls-cern-sso
Oracle Weblogic CERN SSO integration packages

What?

Oracle Weblogic Server implements out-of-the-box the [web browser SSO profile of SAML V2.0](http://docs.oasis-open.org/security/saml/Post2.0/sstc-saml-tech-overview-2.0-cd-02.html#5.1.Web%20Browser%20SSO%20Profile|outline). However there are two gaps that need to be filled:
1. How to get the user attributes from the IdP SAMLResponse: https://github.com/cerndb/wls-cern-sso/tree/master/WlsAttributeNameMapper
2. The [Single Log Out profile](http://docs.oasis-open.org/security/saml/Post2.0/sstc-saml-tech-overview-2.0-cd-02.html):https://github.com/cerndb/wls-cern-sso/tree/master/saml2slo


