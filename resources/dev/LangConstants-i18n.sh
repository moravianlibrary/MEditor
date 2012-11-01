#!/bin/sh
if [ -z "$GWT_HOME" ] ; then
        echo "Envrironment variable GWT_HOME is not set"
        exit 1
fi

APPDIR=`dirname $0`/../../editor-confutils;
PATH_TO_CONSTANTS=/src/main/resources/cz/mzk/editor/client
PATH_TO_JAVA_CLASS=/target/generated-sources/gwt

cp $APPDIR"$PATH_TO_CONSTANTS"/LangConstants_en.properties $APPDIR"$PATH_TO_CONSTANTS"/LangConstants.properties
mkdir -p $APPDIR/src/cz/mzk/editor/client
cp $APPDIR"$PATH_TO_CONSTANTS"/*.properties $APPDIR/src/cz/mzk/editor/client/

java -cp "$APPDIR/src:$APPDIR/bin:$GWT_HOME/gwt-user.jar:$GWT_HOME/gwt-dev.jar" com.google.gwt.i18n.tools.I18NSync -out $APPDIR"$PATH_TO_JAVA_CLASS"  cz.mzk.editor.client.LangConstants ;

rm -r $APPDIR/src/cz

echo "i18n has been generated"
