#!/bin/bash
#PLEASE NOTE: THIS FILE HAS SUDO PRIVILEDGES TO ALL
#(an entry exists in visudo `ALL ALL=NOPASSWD: /path/to/this/file`)
#IT MUST THEREFORE BE WRITE PROTECTED!

#Postgres uses the OS cache for it's caching.
#The only way to wipe the postgres cache is to wipe the OS cache.
#During our experiments, we do not want caching
#so we run this script to wipe them.

echo "Clearing Postgres Cache";

sync;

systemctl stop postgresql.service;

echo 1 > /proc/sys/vm/drop_caches;

systemctl start postgresql.service;

RETRIES=40
until psql -U postgres -d dbconsent -c "select 1" > /dev/null 2>&1 || [ $RETRIES -eq 0 ]; do
  echo "Waiting for postgres server, $((RETRIES--)) remaining attempts..."
  sleep 1
  systemctl start postgresql.service;
done
