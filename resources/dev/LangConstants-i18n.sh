#!/bin/sh
if [ -z "$GWT_HOME" ] ; then
	echo "Envrironment variable GWT_HOME is not set"
	exit 1
fi

APPDIR=`dirname $0`/../..;
cp $APPDIR/src/cz/mzk/editor/client/LangConstants_en.properties $APPDIR/src/cz/mzk/editor/client/LangConstants.properties
java -cp "$APPDIR/src:$APPDIR/bin:$GWT_HOME/gwt-user.jar:$GWT_HOME/gwt-dev.jar" com.google.gwt.i18n.tools.I18NSync -out $APPDIR/src cz.mzk.editor.client.LangConstants  ;
echo "i18n has been generated"
