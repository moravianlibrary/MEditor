#!/bin/bash
if [ -n "$TOMCAT_PASSWORD" ] ; then
  mv /tmp/tomcat-users.xml "$CATALINA_HOME/conf/tomcat-users.xml";
  sed -i -e "s|\${TOMCAT_PASSWORD}|$TOMCAT_PASSWORD|" \
     "$CATALINA_HOME/conf/tomcat-users.xml";
fi


exec "$@"
