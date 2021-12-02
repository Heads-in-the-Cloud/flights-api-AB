#!/bin/sh

[ 403 -eq $(curl -X GET -s -o /dev/null -w '%{http_code}' http://localhost:$1/api/flights) ]
