#!/bin/sh
docker run -v /kramerius-data:/kramerius-data --dns="8.8.8.8" -t -i --name="kramerius-dev" -d -p 81:80 moravianlibrary/kramerius-dev /init.sh
docker run -v /meditor-data:/meditor-data -v /imageserver-data:/imageserver-data --dns="8.8.8.8" -t -i --name="meditor-dev"  -p 5432:5432 moravianlibrary/meditor-dev /init.sh
