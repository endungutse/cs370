#!/bin/bash

function cleanup {
	kill $server_pid
	rm gradle.properties
	}
	
trap "cleanup " INT TERM EXIT 

function unkown_files {
unkown_file_count='git status --porcelain | grep "^??" | wc -l'
[[ "$unkown_file_count" -gt 0]]
}

function uncomitted changes {
	count='git diff HEAD --shortstat | wc -l'
	[[ '$count" -gt 0]]
}

if unknown_files; then 
echo "unkown files in project!"
exit 1
fi
if uncomitted_changes; then
	echo "Uncomitted files in project
	exit 1
fi

gradle clean build
if [ "$?" -gt 0 ]; then
 exit 1
 fi
 
 gradle gaeRun &> /dev/null &
 server_pid=$!
  if [ "$?" -gt 0 ]; then
  echo "server failed to start"
 exit 1
 fi
 
 server_status=1
 echo -n "Waiting for local server to start..."
 while [ ! server_status -gt 0]; do
 	echo -n .
 curl http://localhost:8085/checkclearing
 server_status=$?
 sleep1
 done
 
 kill $server_pid
 
 echo "Build successful! Enter AppEngine password to deploy"
 stty -echo
 read -p "Password: " password
 echo
 stty echo
 gradle gaeUpload
 rm gradle.properties
  
 
echo "Exiting ..."	
response= `curl -s -H Content-Type:application/json -d '["one"]' http://$server/checkclearing`
if [ $response = '{"one":100}' ]; then
	#echo $response
	echo "TEST succeeded!!!!!!!!!!!!!!"
	exit 1
else
	echo "TEST failed"
fi

