#!/bin/bash

server=${1-localhost:8085}

response= `curl -s -H Content-Type:application/json -d '["one"]' http://$server/checkclearing`
if [ $response = '{"one":100}' ]; then
	#echo $response
	echo "TEST succeeded!!!!!!!!!!!!!!"
	exit 1
else
	echo "TEST failed"
fi
