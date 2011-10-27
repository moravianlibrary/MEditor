#!/bin/sh
# setup environment variables for shell script
CATALINA_HOME=/home/meditor/apache-tomcat-7.0.2



# Define DJATOKA_HOME dynamically
#set -x
CUR_DIR=`dirname $0`
LAUNCHDIR=$PWD
if [ -z "$DJATOKA_HOME" ] ; then
	DJATOKA_HOME=$CUR_DIR/..
fi
LIBPATH=$DJATOKA_HOME/lib

if [ `uname` = 'Linux' ] ; then
  if [ `uname -p` = "x86_64" ] ; then
    # Assume Linux AMD 64 has 64-bit Java
    PLATFORM="Linux-x86-64"
    LD_LIBRARY_PATH="$LIBPATH/$PLATFORM"
    export LD_LIBRARY_PATH
    KAKADU_LIBRARY_PATH="-DLD_LIBRARY_PATH=$LIBPATH/$PLATFORM"
  else
    # 32-bit Java
    PLATFORM="Linux-x86-32"
    LD_LIBRARY_PATH="$LIBPATH/$PLATFORM"
    export LD_LIBRARY_PATH
    KAKADU_LIBRARY_PATH="-DLD_LIBRARY_PATH=$LIBPATH/$PLATFORM"
  fi
elif [ `uname` = 'Darwin' ] ; then
  # Mac OS X
  PLATFORM="Mac-x86"
  export PATH="/System/Library/Frameworks/JavaVM.framework/Versions/1.5/Home/bin:$PATH"
  export DYLD_LIBRARY_PATH="$LIBPATH/$PLATFORM"
  KAKADU_LIBRARY_PATH="-DDYLD_LIBRARY_PATH=$LIBPATH/$PLATFORM"
elif [ `uname` = 'SunOS' ] ; then
  if [ `uname -p` = "i386" ] ; then
    # Assume Solaris x86
    PLATFORM="Solaris-x86"
    LD_LIBRARY_PATH="$LIBPATH/$PLATFORM"
    export LD_LIBRARY_PATH
  else
    # Sparcv9
    PLATFORM="Solaris-Sparcv9"
    LD_LIBRARY_PATH="$LIBPATH/$PLATFORM"
    export LD_LIBRARY_PATH
  fi
else
  echo "djatoka env: Unsupported platform: `uname`"
  exit
fi

KAKADU_HOME=$DJATOKA_HOME/bin/$PLATFORM
export KAKADU_HOME

cd $CUR_DIR
for line in `ls -1 $LIBPATH | grep '.jar'`
  do
  classpath="$classpath:$LIBPATH/$line"
done
#DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"
CLASSPATH=.:../build/:$classpath
JAVA_OPTS="$DEBUG -Djava.awt.headless=true  -Xmx512M -Xms64M -Dkakadu.home=$KAKADU_HOME -Djava.library.path=$LIBPATH/$PLATFORM $KAKADU_LIBRARY_PATH"

# If a proxy server is used in your institution... uncomment and set the following:
#proxySet=true
#proxyPort=8080
#proxyHost=proxyout.lanl.gov
#JAVA_OPTS="$JAVA_OPTS -DproxySet=$proxySet -DproxyPort=$proxyPort -DproxyHost=$proxyHost"
