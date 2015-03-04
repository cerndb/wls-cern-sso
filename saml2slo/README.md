# saml2slo

## What?

This [servlet](http://docs.oracle.com/javaee/7/api/javax/servlet/Servlet.html) performs this two actions:

1. It kills the user session in the Oracle Weblogic Server.
2. It informs the SSO that the *'local'* session has been killed. Hence the SSO can continue with the process of logout.

This [diagram](http://docs.oasis-open.org/security/saml/Post2.0/sstc-saml-tech-overview-2.0-cd-02_html_m50a2ba3e.gif) shows how this logout process works.

## Compilation and packaging

The source code has been compiled using Java 7:

```
java version "1.7.0_67"
Java(TM) SE Runtime Environment (build 1.7.0_67-b01)
Java HotSpot(TM) 64-Bit Server VM (build 24.65-b04, mixed mode)
```

You will need to add the next libraries to your **classpath**:

* [$WL_HOME/server/lib/api.jar](http://docs.oracle.com/middleware/1212/wls/NOTES/index.html#CJAEGAAB) 
* **$WL_HOME/server/lib/api.jar**: it loads the **Servlet API** among many others (take a look at its *MANIFEST.MF*).
* **$WL_HOME/modules/com.bea.core.utils_2.3.0.0.jar**: it contains utility classes like *weblogic.utils.encoders.BASE64Encoder*
* **saml2slo/WEB-INF/lib/bcprov-jdk15-1.46.jar**: this library stores the *org.bouncycastle.util.encoders.Hex* class used for the hex encoded of the *ID* element of the *samlp:LogoutRequest*. See more at [The Legion of the Bouncy Castle](https://www.bouncycastle.org/java.html)
* **saml2slo/WEB-INF/lib/commons-codec-1.3.jar**: I use the *org.apache.commons.codec.binary.Base64* from this .jar for verifying the URL signature of the logout request and for decoding/encoding and inflating/deflating the responses and requests.

The library is packaged as a **.war**. You can use the **war** [ant target](https://ant.apache.org/manual/targets.html) of the [build.xml](https://github.com/cerndb/wls-cern-sso/blob/master/WlsAttributeNameMapper/build.xml). Remember to update the **classpath** [fileset](https://ant.apache.org/manual/Types/fileset.html) of the compile target with above jars.

If you want to test the **JMX** connection with your Oracle Weblogic Server, you can run the **saml2slo/test/JMXclient** class. It requires these other libraries in your **classpath**:

* **$WL_HOME/modulescom.bea.core.weblogic.security.identity_3.1.0.0.jar**
* **$WL_HOME/server/lib/weblogic.jar**
* **$WL_HOME/server/lib/wljmxclient.jar**

## Installation and configuration

* Add the saml2slo endpoint to the [Weblogic server federation services metadata](http://docs.oracle.com/cd/E24329_01/web.1211/e24422/saml.htm#SECMG284):

```xml
<md:SingleLogoutService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect" Location="https://your.domain/saml2slo/sp"/>
```

* **Deploy** the **saml2slo.war** to your Weblogic server

The configuration is done via **<context-param>** elements in the [web.xml](https://github.com/cerndb/wls-cern-sso/blob/master/saml2slo/WebContent/WEB-INF/web.xml) deployment descriptor:

* **algorithm**: signature algorithm used for generating the ** samlp:LogoutResponse** to the SSO.
* **SigAlg**: signature algorithm specified in the logout request/response **URL**.
* **idpEndpoint**: SSO endpoint where to send the **samlp:LogoutResponse**.
* **keystoreType**: JKS.
* **keystoreProvider**: SUN.
* **getPasswdScript**: I used a script for get the keystore and key passwords. This parameters tells the application where it is. 
* **keystorePasswordKey**: this value will be the getPasswdScript parameter for getting the keystore password.
* **privateKeyPasswordKey**: this value will be the getPasswdScript parameter for getting the Weblogic SP key password.
* **ssoSignOutUrl**: SSO Single Log Out URL. I used it to initialize the redirect URL used by the [SAML2sloServlet](https://github.com/cerndb/wls-cern-sso/blob/master/saml2slo/src/ch/cern/security/saml2/servlet/SAML2sloServlet.java) to redirect the response. It will be override by the idpEndpoint + samlp:LogoutResponse.
* **webSSOpartnerName**: name of the [SAML 2.0 Identity Assertion Provider](https://docs.oracle.com/middleware/1212/wls/SECMG/atn.htm#SECMG215)
* **authenticationProviderName**: name of the [SAML 2.0 identity provider partner](http://docs.oracle.com/cd/E24329_01/apirefs.1211/e24401/taskhelp/security/ConfigureAuthenticationProviders.html)

## And what do I need in my application?

Just add somewhere a *link* to your SSO *logout URL*. E.g. for the [CERN](http://home.web.cern.ch/) applications:

```html
<a href="https://login.cern.ch/adfs/ls/?wa=wsignout1.0">Sign out</a>
```


