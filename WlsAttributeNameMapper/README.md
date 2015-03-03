# WlsAttributeNameMapper

## What?

This library allows the server to extract the information from the SSO response and create the [java.security.Principal](http://docs.oracle.com/javase/7/docs/api/java/security/Principal.html) objects that identify the authenticated user ([subject](http://docs.oracle.com/javase/7/docs/api/javax/security/auth/Subject.html)).

## Compilation and packaging

The source code has been compiled using Java 7:

```
java version "1.7.0_67"
Java(TM) SE Runtime Environment (build 1.7.0_67-b01)
Java HotSpot(TM) 64-Bit Server VM (build 24.65-b04, mixed mode)
```

You will need to add the [wls-api.jar](http://docs.oracle.com/middleware/1212/wls/NOTES/index.html#CJAEGAAB) to your **classpath**. 

The library is packaged as a **.jar**. You can use the [ant target](https://ant.apache.org/manual/targets.html) of the [build.xml](https://github.com/cerndb/wls-cern-sso/blob/master/WlsAttributeNameMapper/build.xml). Remember to update the **classpath** [fileset](https://ant.apache.org/manual/Types/fileset.html) of the compile target with above jar.    

## Installation

Add the classes to the [Oracle Weblogic system classpath](http://docs.oracle.com/middleware/1212/wls/WLPRG/classloading.htm#WLPRG282). To do this you can override the value of the **EXT_POST_CLASSPATH** variable in the **setDomainEnv.sh** script with the path to the compiled package:

```shell
EXT_POST_CLASSPATH="/path/to/your/WlsAttributeNameMapper.jar"
if [ "${EXT_POST_CLASSPATH}" != "" ] ; then
        if [ "${POST_CLASSPATH}" != "" ] ; then
                POST_CLASSPATH="${POST_CLASSPATH}${CLASSPATHSEP}${EXT_POST_CLASSPATH}"
                export POST_CLASSPATH
        else
                POST_CLASSPATH="${EXT_POST_CLASSPATH}"
                export POST_CLASSPATH
        fi
fi
```
Once your system classpath is set declare the mapper class into the **User Name Mapper Class Name** field of your **SSO Identity Asserter**. See [Configure a custom user name mapper](http://docs.oracle.com/cd/E24329_01/apirefs.1211/e24401/taskhelp/security/ConfigureCustomUserNameMappers.html) for more information. You can choose between two implementations of the mapper:

1. [CernWlsPrincipalMapper](https://github.com/cerndb/wls-cern-sso/blob/master/WlsAttributeNameMapper/src/ch/cern/sso/weblogic/mappers/CernWlsPrincipalMapper.java): it creates one [CernWlsUserPrincipal](https://github.com/cerndb/wls-cern-sso/blob/master/WlsAttributeNameMapper/src/ch/cern/sso/weblogic/principals/CernWlsUserPrincipal.java) with the user's attributes (name, email, phone number...) plus one [CernWlsGroupPrincipal](https://github.com/cerndb/wls-cern-sso/blob/master/WlsAttributeNameMapper/src/ch/cern/sso/weblogic/principals/CernWlsGroupPrincipal.java) per user's group.
2. [CernWlsUserPrincipalMapper](https://github.com/cerndb/wls-cern-sso/blob/master/WlsAttributeNameMapper/src/ch/cern/sso/weblogic/mappers/CernWlsUserPrincipalMapper.java): it creates one [CernWlsUserPrincipal](https://github.com/cerndb/wls-cern-sso/blob/master/WlsAttributeNameMapper/src/ch/cern/sso/weblogic/principals/CernWlsUserPrincipal.java) with the user's attributes, including an [ArrayList](http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html) of strings with all the groups.

## How can I get this information in my application?

```java
import java.security.Principal;
import weblogic.security.Security;
import javax.security.auth.Subject;
import ch.cern.sso.weblogic.principals.CernWlsUserPrincipal;

// .../...
Subject subject = Security.getCurrentSubject();
Set<Principal> principals = subject.getPrincipals();
for (Iterator iterator = principals.iterator(); iterator.hasNext();) {
	Principal principal = (Principal) iterator.next();
	if (principal instanceof CernWlsUserPrincipalMapper) {
		//
	}
}
``` 

