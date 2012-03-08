#!/bin/sh

EDITOR_USER=meditor
CUR_DIR=`dirname $0`
. $CUR_DIR/env.sh

#echo $JAVA_OPTS
#echo $DYLD_LIBRARY_PATH
#echo $KAKADU_HOME


if [ "x$EDITOR_USER" != "x$USER" ]; then
   echo "run the script as a user $EDITOR_USER"
   exit 1
fi


if [ "x" = "x$CATALINA_HOME" ]; then
   echo "CATALINA_HOME not defined, set system variable and try again"
   exit 2
fi

export JAVA_OPTS

rm -f $CATALINA_HOME/temp/*
$CATALINA_HOME/bin/catalina.sh $*

exit 0
