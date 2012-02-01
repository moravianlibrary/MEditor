#!/bin/sh
#  Deploy script for metadata editor
#
#
#  author: Jiri Kremser

set -x
#MEDITOR_HOME=~/workspace/MEditor
MEDITOR_HOME=`dirname $0`/../..
WAR_NAME=meditor

#vsup setup
#USER=kramerius4
#HOST=192.168.8.9
#TOMCAT=fedora/tomcat
#SCRIPT=/home/kramerius4/.meditor/djatoka/bin/tomcat.sh

#mzk setup
USER=meditor
HOST=editor.mzk.cz
TOMCAT=apache-tomcat-7.0.2
SCRIPT=/home/meditor/.meditor/djatoka/bin/tomcat.sh




DEPLOY="true"
#DEPLOY="false"
echo "getting svn revision number..."
REVISION=`svn info https://meta-editor.googlecode.com/svn/ |grep '^Revision:' | sed -e 's/^Revision: //'`
echo "svn revison is $REVISION"

echo "Copying echo ~/workspace/MEditor/war ..."
cp $MEDITOR_HOME/resources/dev/log4j.properties $MEDITOR_HOME/resources/dev/schemaVersion.txt $MEDITOR_HOME/resources/dev/schema.sql $MEDITOR_HOME/war/WEB-INF/classes
cp -r $MEDITOR_HOME/war $MEDITOR_HOME/war2

echo "Removing .svn directories..."
rm -Rf `find $MEDITOR_HOME/war2 -name \.svn`
echo "<h1>Revision $REVISION</h2>" > $MEDITOR_HOME/war2/version.html
[ "$DEPLOY" = "true" ] && {
	echo "Shutting down Tomcat..."
	ssh $USER@$HOST $SCRIPT stop -force 
	sleep 1
	echo "Tomcat is down"
}
cd $MEDITOR_HOME/war2
set +x
echo "ted to muze vypsat nejake chyby, ale to jsou hodne chyby"
echo "Packing war file..."
`zip -r ../$WAR_NAME.war * &> /dev/null`
echo "War file has been made."
set -x
echo "konec hodnych chyb"
cd -
[ "$DEPLOY" = "true" ] && {
	echo "Backup.."
	ssh $USER@$HOST "mv /home/$USER/$TOMCAT/webapps/$WAR_NAME.war_old /home/$USER/$TOMCAT/webapps/$WAR_NAME.war_old_bak"
	ssh $USER@$HOST "mv /home/$USER/$TOMCAT/webapps/$WAR_NAME.war /home/$USER/$TOMCAT/webapps/$WAR_NAME.war_old"
	ssh $USER@$HOST "rm -Rf /home/$USER/$TOMCAT/webapps/$WAR_NAME"
	echo "Deploy.."
	scp $MEDITOR_HOME/$WAR_NAME.war $USER@$HOST:/home/$USER/$TOMCAT/webapps/$WAR_NAME.war
	echo "Running server"
	ssh $USER@$HOST $SCRIPT start	
	rm $MEDITOR_HOME/$WAR_NAME.war
}
rm -Rf $MEDITOR_HOME/war2
