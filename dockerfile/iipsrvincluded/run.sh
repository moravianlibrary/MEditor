#!/bin/sh
docker run -v /meditor-data:/meditor-data -v /imageserver-data:/imageserver-data  -t -i -p 8080:8080 -p 80:80 -p 443:443 -p 8001:8000 meditor-iipsrv /bin/bash
