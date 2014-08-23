#!/bin/sh
docker run -v /kramerius-data:/kramerius-data --dns="8.8.8.8" -t -i --name="kramerius" -d -p 81:80 kramerius-dev /init.sh
docker run -v /meditor-data:/meditor-data -v /imageserver-data:/imageserver-data --dns="8.8.8.8" -t -i --name="meditor" --link kramerius:kramerius -d -p 8080:8080 -p 80:80 -p 443:443 -p 8000:8000 meditor-dev  /init.sh

