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

* [wls-api.jar](http://docs.oracle.com/middleware/1212/wls/NOTES/index.html#CJAEGAAB) 

The library is packaged as a **.war**. You can use the [ant target](https://ant.apache.org/manual/targets.html) of the [build.xml](https://github.com/cerndb/wls-cern-sso/blob/master/WlsAttributeNameMapper/build.xml). Remember to update the **classpath** [fileset](https://ant.apache.org/manual/Types/fileset.html) of the compile target with above jar.    

## Installation and configuration


