#!/bin/bash

# workaround files owner problem
chown -R meditor:meditor /home/meditor/

if [ ! -d "/meditor-data/import" ]; then
  mkdir /meditor-data/import
fi

if [ ! -d "/meditor-data/postgres-data" ]; then
  mkdir /meditor-data/postgres-data
  mv /var/lib/postgresql/9.1/main /meditor-data/postgres-data/main
fi

if [ ! -d "/meditor-data/.meditor" ]; then
  mv /home/meditor/.meditorDefault /meditor-data/.meditor
fi

if [ ! -d "/meditor-data/.meditor/output" ]; then
  mkdir /meditor-data/.meditor/output
  chown meditor:meditor /meditor-data/.meditor/output
fi

/etc/init.d/postgresql start && /etc/init.d/apache2 start && su -l -m meditor -c "/home/meditor/apache-tomcat-7.0.54/bin/startup.sh"

while :; do /bin/bash; sleep 1; done
