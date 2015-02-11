#!/bin/bash
# author Martin Rumanek
# script for convert audio files

if [ $# -ne 2 ]; then
        echo "ERROR: Missing arguments"
        echo "Usage: convert.sh input_audio output_audio_basename"
        exit 5
fi

function mp3 {
    cp $1 $2.mp3
    ffmpeg -y -i  $1 -acodec libvorbis -aq 5 $2.ogg
    ffmpeg -y -i $1 -acodec pcm_u8  $2.wav
}
function wav {
    cp $1 $2.wav
    ffmpeg -y -i  $1 -acodec libmp3lame $2.mp3
    ffmpeg -y -i  $1 -acodec libvorbis -aq 5 $2.ogg
}


case $1 in
    *.wav) wav $1 $2 ;;
    *.mp3) mp3 $1 $2 ;;

esac

exit 0
