#!/bin/sh
docker run -v /meditor-data:/meditor-data -v /imageserver-data:/imageserver-data --dns="8.8.8.8" -t -i -p 80:80 -p 443:443 -p 8000:8000 meditor /init.sh
