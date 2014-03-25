#!/bin/sh
docker run -v meditor-data:/meditor-data  -t -i -p 8080:8080 -p 80:80 -p 443:443 -p 8000:8000 meditor /bin/bash
