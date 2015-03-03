# WlsAttributeNameMapper

## What?

This library allows the server to extract the information from the SSO response and create the [java.security.Principal](http://docs.oracle.com/javase/7/docs/api/java/security/Principal.html) objects that identify the authenticated user.

## Compilation and packaging

The source code has been compiled using Java 7:

```
java version "1.7.0_67"
Java(TM) SE Runtime Environment (build 1.7.0_67-b01)
Java HotSpot(TM) 64-Bit Server VM (build 24.65-b04, mixed mode)
```

You will need to add the [wls-api.jar](http://docs.oracle.com/middleware/1212/wls/NOTES/index.html#CJAEGAAB) to your **classpath**. 

## Installation

Add the classes to the [Oracle Weblogic system classpath](http://docs.oracle.com/middleware/1212/wls/WLPRG/classloading.htm#WLPRG282). To do this you can override the value of the **EXT_POST_CLASSPATH** variable in the **setDomainEnv.sh** script:

```
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

