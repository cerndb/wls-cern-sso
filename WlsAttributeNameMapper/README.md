# WlsAttributeNameMapper

## What?

This library allows the server to extract the information from the SSO response and create the [java.security.Principal](http://docs.oracle.com/javase/7/docs/api/java/security/Principal.html) objects that identify the authenticated user.

## Compilation and packaging

Ant

## Installation

Add the classes to the [Oracle Weblogic system classpath](http://docs.oracle.com/middleware/1212/wls/WLPRG/classloading.htm#WLPRG282). To do this you can override the value of the *(EXT_POST_CLASSPATH)* variable in the *setDomainEnv.sh* script:

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

