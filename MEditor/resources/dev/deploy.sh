#!/bin/sh
#  Deploy script for metadata editor
#
#
#  author: Jiri Kremser

MEDITOR_HOME=`dirname $0`/../..
WAR_NAME=meditor

#mzk setup
MZK_USER=meditor
MZK_HOST=editor.mzk.cz
MZK_TOMCAT=apache-tomcat-7.0.25
MZK_SCRIPT=.meditor/djatoka/bin/tomcat.sh

#dev setup
DEV_USER=meditor
DEV_HOST=editor-devel.mzk.cz
DEV_TOMCAT=apache-tomcat-7.0.2
DEV_SCRIPT=.meditor/djatoka/bin/tomcat.sh

#vsup setup
VSUP_USER=kramerius4
VSUP_HOST=192.168.8.9
VSUP_TOMCAT=fedora/tomcat
VSUP_SCRIPT=.meditor/djatoka/bin/tomcat.sh

#prints usage
usage () {
  echo "Usage: $0 deployment_schema [war]"
  echo "   Available deployment schemas: "
  printf "     mzk    (%-30s tomcat=%-22s script=%-30s\n" "$MZK_USER@$MZK_HOST" "$MZK_TOMCAT" "$MZK_SCRIPT"
  printf "     dev    (%-30s tomcat=%-22s script=%-30s\n" "$DEV_USER@$DEV_HOST" "$DEV_TOMCAT" "$DEV_SCRIPT"
  printf "     vsup   (%-30s tomcat=%-22s script=%-30s\n\n" "$VSUP_USER@$VSUP_HOST" "$VSUP_TOMCAT" "$VSUP_SCRIPT"
  echo "   optional argument war builds only the war"
  echo -n "   file but won't deploy it on the server\n"
  exit 1
}

#number of args
[ $# -ne 1 ] && [ $# -ne 2 ] &&  usage 

#profile
case "$1" in
	mzk) 
		echo "Profile mzk"
		USER=$MZK_USER
		HOST=$MZK_HOST
		TOMCAT=$MZK_TOMCAT
		SCRIPT=$MZK_SCRIPT
	;;
	dev) 
	echo "Profile dev"
		USER=$DEV_USER
		HOST=$DEV_HOST
		TOMCAT=$DEV_TOMCAT
		SCRIPT=$DEV_SCRIPT
	;;
	vsup) 
	echo "Profile vsup"
		USER=$VSUP_USER
		HOST=$VSUP_HOST
		TOMCAT=$VSUP_TOMCAT
		SCRIPT=$VSUP_SCRIPT
	;;
	*) usage ;;
esac

#deploy or build only
if [ "$2" != "war" ] ; then
	DEPLOY="true"
else
	DEPLOY="false"
	echo -n "\nno deploying, only building...\n\n"
fi


set -x
echo -n "\n\ngetting svn revision number..."
REVISION=`svn info https://meta-editor.googlecode.com/svn/ |grep '^Revision:' | sed -e 's/^Revision: //'`
echo -n "\nsvn revison is $REVISION"

echo -n "\nCopying echo ~/workspace/MEditor/war ..."
cp $MEDITOR_HOME/resources/dev/log4j.properties $MEDITOR_HOME/resources/dev/schemaVersion.txt $MEDITOR_HOME/resources/dev/schema.sql $MEDITOR_HOME/war/WEB-INF/classes
cp -r $MEDITOR_HOME/war $MEDITOR_HOME/war2

echo -n "Substitution of <<<hostname>>> for $HOST"
sed -i "s/<<<hostname>>>/$HOST/g" $MEDITOR_HOME/war2/login.html
sed -i "s/<<<hostname>>>/$HOST/g" $MEDITOR_HOME/war2/viewer/viewer.html

echo -n "\nRemoving .svn directories..."
rm -Rf `find $MEDITOR_HOME/war2 -name \.svn`
echo "<h1>Revision $REVISION</h2>" > $MEDITOR_HOME/war2/version.html
[ "$DEPLOY" = "true" ] && {
	echo -n "\nShutting down Tomcat..."
	ssh $USER@$HOST /home/$USER/$SCRIPT stop -force 
	sleep 1
	echo -n "\nTomcat is down"
}
cd $MEDITOR_HOME/war2
set +x
echo -n "\nted to muze vypsat nejake chyby, ale to jsou hodne chyby\n"
echo "Packing war file..."
`zip -r ../$WAR_NAME.war * &> /dev/null`
echo -n "\nWar file has been made."
set -x
echo -n "\nkonec hodnych chyb"
cd -
[ "$DEPLOY" = "true" ] && {
	echo -n "\nBackup.."
	ssh $USER@$HOST "mv /home/$USER/$TOMCAT/webapps/$WAR_NAME.war_old /home/$USER/$TOMCAT/webapps/$WAR_NAME.war_old_bak"
	ssh $USER@$HOST "mv /home/$USER/$TOMCAT/webapps/$WAR_NAME.war /home/$USER/$TOMCAT/webapps/$WAR_NAME.war_old"
	ssh $USER@$HOST "rm -Rf /home/$USER/$TOMCAT/webapps/$WAR_NAME"
	echo -n "\nDeploy.."
	scp $MEDITOR_HOME/$WAR_NAME.war $USER@$HOST:/home/$USER/$TOMCAT/webapps/$WAR_NAME.war
	echo -n "\nRunning server"
	ssh $USER@$HOST /home/$USER/$SCRIPT start	
	rm $MEDITOR_HOME/$WAR_NAME.war
}
rm -Rf $MEDITOR_HOME/war2
echo -n "\nDone.\n"
