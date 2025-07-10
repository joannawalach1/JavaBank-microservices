#!/bin/bash
echo "Czekam na Cassandrę..."
until cqlsh -e "DESCRIBE KEYSPACES"; do
  sleep 2
done

echo "Wykonuję init.cql..."
cqlsh -f /init.cql
