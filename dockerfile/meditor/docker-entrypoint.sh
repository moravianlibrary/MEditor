#!/bin/bash
if [ -n "$TOMCAT_PASSWORD" ] ; then
  mv /tmp/tomcat-users.xml "$CATALINA_HOME/conf/tomcat-users.xml";
  sed -i -e "s|\${TOMCAT_PASSWORD}|$TOMCAT_PASSWORD|" \
     "$CATALINA_HOME/conf/tomcat-users.xml";
fi

if [ -d /data/meditor/xsl ]; then

  if [ -e /data/meditor/xsl/MARC21slim2MODS3.xsl ]; then
    rm /root/.meditor/xml/MARC21slim2MODS3.xsl
  else
    mv /root/.meditor/xml/MARC21slim2MODS3.xsl /data/meditor/xsl/MARC21slim2MODS3.xsl
  fi
  ln -s /data/meditor/xsl/MARC21slim2MODS3.xsl /root/.meditor/xml/MARC21slim2MODS3.xsl

  if [ -e /data/meditor/xsl/MARC21slim2OAIDC.xsl ]; then
    rm /root/.meditor/xml/MARC21slim2OAIDC.xsl
  else
    mv /root/.meditor/xml/MARC21slim2OAIDC.xsl /data/meditor/xsl/MARC21slim2OAIDC.xsl
  fi
  ln -s /data/meditor/xsl/MARC21slim2OAIDC.xsl /root/.meditor/xml/MARC21slim2OAIDC.xsl

  if [ -e /data/meditor/xsl/MARC21slimUtils.xsl ]; then
    rm /root/.meditor/xml/MARC21slimUtils.xsl
  else
    mv /root/.meditor/xml/MARC21slimUtils.xsl /data/meditor/xsl/MARC21slimUtils.xsl
  fi
  ln -s /data/meditor/xsl/MARC21slimUtils.xsl /root/.meditor/xml/MARC21slimUtils.xsl

fi

# converted images in persistent storage
rm -rf /root/output

if [ ! -d /data/meditor/temp ]; then
  mkdir /data/meditor/temp
fi

if [ ! -d /data/meditor/import ]; then
  mkdir /data/meditor/import
fi


ln -s /data/meditor/temp /root/output

cp /data/meditor/.keystore /root/

exec "$@"