#!/bin/sh
docker build -t meditor ../default/
docker build -t meditor-iipsrv ../iipsrvincluded/
docker build -t meditor-dev .

