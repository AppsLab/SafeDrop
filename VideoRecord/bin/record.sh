#!/bin/sh

#/home/root/bin/ffmpeg/ffmpeg -s 320x240 -f video4linux2 -i /dev/video0 -f mpeg1video \
#-b:v 800k -r 24 -vcodec mpeg4 -t 00:00:10 -y /home/root/edi-cam/web/client/output.mp4

/home/root/bin/ffmpeg/ffmpeg -s 320x240 -f video4linux2 -i /dev/video0 -b:v 800k -r 24 -vcodec mpeg4 -t 00:00:10 -y /home/root/edi-cam/web/client/output.mp4
/home/root/bin/ffmpeg/ffmpeg -i /home/root/edi-cam/web/client/output.mp4 -c:v libx264 -crf 22 -y /home/root/edi-cam/web/client/record.mp4



