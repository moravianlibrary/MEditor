#!/bin/bash

VERSION=$1

if [[ -z "$VERSION" ]]; then
    echo "no version specified"
    exit 1
fi

mvn com.isomorphic:isc-maven-plugin:install -Dproduct=SMARTGWT -Dlicense=LGPL -DbuildNumber=5.0p -DbuildDate=2015-11-29
mkdir ~/.meditor && cp -r resources/xml/ ~/.meditor
mvn clean install
cp target/meditor.war dockerfile/meditor/
docker build -t moravianlibrary/meditor:${VERSION} dockerfile/meditor
docker push moravianlibrary/meditor:${VERSION}
