#!/bin/sh
APPDIR=`dirname $0`/../..;
cp $APPDIR/src/cz/fi/muni/xkremser/editor/client/LangConstants_en.properties $APPDIR/src/cz/fi/muni/xkremser/editor/client/LangConstants.properties
java -cp "$APPDIR/src:$APPDIR/bin:/home/kremser/install/gwt-2.2.0/gwt-user.jar:/home/kremser/install/gwt-2.2.0/gwt-dev.jar" com.google.gwt.i18n.tools.I18NSync -out $APPDIR/src cz.fi.muni.xkremser.editor.client.LangConstants  ;
echo "i18n has been generated"
