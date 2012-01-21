#!/bin/sh
set -x
#MEDITOR_HOME=~/workspace/MEditor
MEDITOR_HOME=~/workspace2/MEditor
WAR_NAME=meditor

#vsup setup
USER=kramerius4
HOST=192.168.8.9
TOMCAT=fedora/tomcat
SCRIPT=/home/kramerius4/.meditor/djatoka/bin/tomcat.sh

#mzk setup
#USER=meditor
#HOST=editor.mzk.cz
#TOMCAT=apache-tomcat-7.0.2
#SCRIPT=/home/meditor/.meditor/djatoka/bin/tomcat.sh




DEPLOY="true"
echo "getting svn revision number..."
REVISION=`svn info https://meta-editor.googlecode.com/svn/ |grep '^Revision:' | sed -e 's/^Revision: //'`
echo "svn revison is $REVISION"

echo "Copying echo ~/workspace/MEditor/war ..."
cp log4j.properties resources/dev/schemaVersion.txt resources/dev/sqema.sql war/WEB-INF/classes
cp -r $MEDITOR_HOME/war $MEDITOR_HOME/war2
cd $MEDITOR_HOME/war2
echo "Removing .svn directories..."
rm -Rf `find . -name \.svn`
echo "<h1>Revision $REVISION</h2>" > version.html
[ "$DEPLOY" = "true" ] && {
	echo "Shutting down Tomcat..."
	ssh $USER@$HOST $SCRIPT stop -force 
	sleep 1
	echo "Tomcat is down"
}
set +x
echo "ted to muze vypsat nejake chyby, ale to jsou hodne chyby"
echo "Packing war file..."
`zip -r ../$WAR_NAME.war * &> /dev/null`
echo "War file has been made."
set -x
echo "konec hodnych chyb"
[ "$DEPLOY" = "true" ] && {
	echo "Backup.."
	ssh $USER@$HOST "mv /home/$USER/$TOMCAT/webapps/$WAR_NAME.war_old /home/$USER/$TOMCAT/webapps/$WAR_NAME.war_old_bak"
	ssh $USER@$HOST "mv /home/$USER/$TOMCAT/webapps/$WAR_NAME.war /home/$USER/$TOMCAT/webapps/$WAR_NAME.war_old"
	ssh $USER@$HOST "rm -Rf /home/$USER/$TOMCAT/webapps/$WAR_NAME"
	echo "Deploy.."
	scp ../$WAR_NAME.war $USER@$HOST:/home/$USER/$TOMCAT/webapps/$WAR_NAME.war
	echo "Running server"
	ssh $USER@$HOST $SCRIPT start	
	rm ../$WAR_NAME.war
}
rm -Rf $MEDITOR_HOME/war2
cd -
