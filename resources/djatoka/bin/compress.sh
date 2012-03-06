#!/bin/bash
# author Jiri Kremser
# script for conversion to JPEG 2000 format
# setup environment variables for shell script

#set -x
DJATOKA_HOME=$1
LIBPATH=$DJATOKA_HOME/lib
TIFF_TMP=""

if [ $# -ne 3 ] ; then
  echo 'Usage: convert.sh path_to_djatoka source_image output_image'
  exit 1
fi

if [ `uname` = 'Linux' ] ; then
  if [ `uname -m` = "x86_64" ] ; then
    # Assume Linux AMD 64 has 64-bit Java
    PLATFORM="Linux-x86-64"
    LD_LIBRARY_PATH="$LIBPATH/$PLATFORM"
    export LD_LIBRARY_PATH
  else
    # 32-bit Java
    PLATFORM="Linux-x86-32"
    LD_LIBRARY_PATH="$LIBPATH/$PLATFORM"
    export LD_LIBRARY_PATH
  fi
elif [ `uname` = 'Darwin' ] ; then
  # Mac OS X
  PLATFORM="Mac-x86"
  export LD_LIBRARY_PATH="$LIBPATH/$PLATFORM"
elif [ `uname` = 'SunOS' ] ; then
  if [ `uname -m` = "i386" ] ; then
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

INPUT_FILE=$(echo $2 | tr "[:upper:]" "[:lower:]")

NO_PROBLEM=""
if [ "${INPUT_FILE:(-4)}x" != ".tifx" ] && [ "${INPUT_FILE:(-5)}x" != "tiffx" ] ; then
  TIFF_TMP1=`tempfile -s ".tif"`;
  TIFF_TMP2=`tempfile -s ".tif"`;
  convert $2 $TIFF_TMP1 && tiffcp -c none $TIFF_TMP1 $TIFF_TMP2 && rm $TIFF_TMP1 && {
    NO_PROBLEM="ok";
  }
fi

if [ "$TIFF_TMP2" != "" ] ; then
  [ "$NO_PROBLEM" != "ok" ] && {
     exit 2
  }
  TIFF_IMG=$TIFF_TMP2
else
  TIFF_IMG=$2
fi

$DJATOKA_HOME/bin/$PLATFORM/kdu_compress -s $DJATOKA_HOME/bin/kakadu.properties -i "$TIFF_IMG" -o "$3"

if [ "$TIFF_TMP2" != "" ] ; then
  rm $TIFF_TMP2
fi

exit 0
