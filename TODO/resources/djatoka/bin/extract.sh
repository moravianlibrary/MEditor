#!/bin/sh

. ./env.sh

$JAVA_HOME/bin/java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.adore.djatoka.DjatokaExtract $*

exit 0
