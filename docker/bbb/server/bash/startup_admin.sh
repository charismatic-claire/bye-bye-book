#!/bin/bash
EXEC=$(ls -1 /usr/bin/server/ | grep jar)
java -Dspring.profiles.active=admin -jar $EXEC
