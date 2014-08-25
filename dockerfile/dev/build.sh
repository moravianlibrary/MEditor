#!/bin/sh
docker build -t moravianlibrary/meditor ../default/
docker build -t moravianlibrary/meditor-iipsrv ../iipsrvincluded/
docker build -t moravianlibrary/meditor-dev .

