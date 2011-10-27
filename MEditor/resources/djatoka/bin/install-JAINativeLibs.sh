#!/bin/sh
# download and install JavaTM Advanced Imaging Binary Builds

if [ -d jni-native ]
then
   echo "djatoka JAI installer: JAI Already Installed"
   exit
fi

if [ `uname` = 'Linux' ] ; then
  if [ `uname -p` = "x86_64" ] ; then
    # Assume Linux AMD 64 has 64-bit Java
    mkdir jni-native
    cd jni-native
    PLATFORM="Linux-x86-64"
    wget "http://download.java.net/media/jai/builds/release/1_1_3/jai-1_1_3-lib-linux-amd64.tar.gz"
    tar -xzf jai-1_1_3-lib-linux-amd64.tar.gz
    cp jai-1_1_3/lib/libmlib_jai.so ../../lib/$PLATFORM/
    rm -f jai-1_1_3-lib-linux-amd64.tar.gz
  else
    mkdir jni-native
    cd jni-native
    # 32-bit Java
    PLATFORM="Linux-x86-32"
    wget "http://download.java.net/media/jai/builds/release/1_1_3/jai-1_1_3-lib-linux-i586.tar.gz"
    tar -xzf jai-1_1_3-lib-linux-i586.tar.gz
    cp jai-1_1_3/lib/libmlib_jai.so ../../lib/$PLATFORM/
    rm -f jai-1_1_3-lib-linux-i586.tar.gz
  fi
elif [ `uname` = 'SunOS' ] ; then
    mkdir jni-native
    cd jni-native
    PLATFORM="Solaris-Sparc"
    wget "http://download.java.net/media/jai/builds/release/1_1_3/jai-1_1_3-lib-solaris-sparc.tar.gz"
    tar -xzf jai-1_1_3-lib-solaris-sparc.tar.gz
    cp jai-1_1_3/lib/libmlib_jai.so ../../lib/$PLATFORM/
    cp jai-1_1_3/lib/libmlib_jai_vis.so ../../lib/$PLATFORM/
    cp jai-1_1_3/lib/libmlib_jai_vis2.so ../../lib/$PLATFORM/
    rm -f jai-1_1_3-lib-solaris-sparc.tar.gz
else
  echo "djatoka JAI installer: Unable to locate JAI native libs for: `uname`, check https://jai.dev.java.net/binary-builds.html for supported platforms"
  exit
fi