# Oracle Weblogic CERN SSO integration packages

# What?

Oracle Weblogic Server implements out-of-the-box the [web browser SSO profile of SAML V2.0](http://docs.oasis-open.org/security/saml/Post2.0/sstc-saml-tech-overview-2.0-cd-02.html#5.1.Web%20Browser%20SSO%20Profile|outline). However there are two gaps that need to be filled:
1. How to get the user information from the IdP SAMLResponse: https://github.com/cerndb/wls-cern-sso/tree/master/WlsAttributeNameMapper
2. The [Single Log Out profile](http://docs.oasis-open.org/security/saml/Post2.0/sstc-saml-tech-overview-2.0-cd-02.html):https://github.com/cerndb/wls-cern-sso/tree/master/saml2slo

# Authors

These libraries have been written by [Luis Rodríguez](https://profiles.web.cern.ch/720335).

# Contributing

I have tried to make the implementation as much general as possible. However it is possible that you have to do some changes in order to make it work to your environment. Please feel free to submit bug reports and pull request via [Github](https://github.com/cerndb/wls-cern-sso)  
