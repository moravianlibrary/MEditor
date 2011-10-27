#!/bin/sh

CUR_DIR=`dirname $0`
. $CUR_DIR/env.sh

#echo $JAVA_OPTS
#echo $DYLD_LIBRARY_PATH
#echo $KAKADU_HOME

if [ "x" = "x$CATALINA_HOME" ]; then
   echo "CATALINA_HOME not defined, set system variable and try again"
   exit
fi

export JAVA_OPTS

rm -f $CATALINA_HOME/temp/*
$CATALINA_HOME/bin/catalina.sh $*

exit 0
