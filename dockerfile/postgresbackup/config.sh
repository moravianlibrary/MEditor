#!/bin/sh

sed -i "s/\${POSTGRES_PASSWORD}/$POSTGRES_PASSWORD/" /etc/cron.d/db-backup
sed -i "s/\${POSTGRES_USER}/$POSTGRES_USER/" /etc/cron.d/db-backup
