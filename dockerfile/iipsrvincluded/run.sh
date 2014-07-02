#!/bin/sh
docker run -v /meditor-data:/meditor-data -v /imageserver-data:/imageserver-data --dns="8.8.8.8" -t -i -p 8080:8080 -p 80:80 -p 443:443  martinrumanek/meditor-iipsrv /init.sh
