#!/bin/sh

/home/root/bin/ffmpeg/ffmpeg -s 320x240 -f video4linux2 -i /dev/video0 -f mpeg1video \
-b:v 800k -r 24 -vcodec mjpeg -t 00:00:10 output2.mp4 
