
#!/bin/bash

server=${1-localhost:8085} #take the first parameter
 
curl -s -H Content-Type:application/json -d '["two million three hundred fourty six thousand + two dollars and 10/100"]' http://$server/checkclearing

history=`curl http://$server/checkclearing`
response=`curl -s -H Content-Type:application/json -d "$history" http://$server/checkclearing`

if [ "$response" != '{"two million three hundred fourty six thousand + two dollars and 10/100"]":234600210}' ]; then
echo $response
echo "TEST FAILED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
exit 1
else
echo "Test succeeded"
fi
