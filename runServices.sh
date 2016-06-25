#!/bin/bash

echo ----------------------------------------------------------------
echo Starting services...
echo \(See readme.txt on how to perform this manually \if needed be\)
echo ----------------------------------------------------------------
echo Starting eureka...
rm eureka_log.txt > /dev/null 2>&1
touch eureka_log.txt
java -jar spring-registration-service-1.0.0-SNAPSHOT.jar > eureka_log.txt &
sleep 18

echo Starting edge service...
rm edge_log.txt > /dev/null 2>&1
touch edge_log.txt
java -jar spring-edge-service-1.0.0-SNAPSHOT.jar > edge_log.txt &
sleep 3

echo Starting image service...
rm image_log.txt > /dev/null 2>&1
touch image_log.txt
java -jar spring-image-service-1.0.0-SNAPSHOT.jar > image_log.txt &
sleep 3

echo Starting user service...
rm user_log.txt > /dev/null 2>&1
touch user_log.txt
java -jar spring-user-service-1.0.0-SNAPSHOT.jar > user_log.txt &
sleep 3

echo Starting comment service...
rm comment_log.txt > /dev/null 2>&1
touch comment_log.txt
java -jar spring-comment-service-1.0.0-SNAPSHOT.jar > comment_log.txt &
sleep 3

echo Starting web service...
rm web_log.txt > /dev/null 2>&1
touch web_log.txt
java -jar spring-web-service-1.0.0-SNAPSHOT.jar > web_log.txt &
sleep 3

echo  
echo All services initiated:
echo - Go to localhost:9998 to see \which services are ready \(takes up to 2 minutes \for all to be ready).
echo - Go to localhost:9999 to see the web application. 
echo  
echo If localhost\:9998 show all 5 services, but localhost\:9999 shows an error-page, it usually means Zuul \(edge-server\) has not yet detected all the services, \try again \in a few seconds.
echo  
echo Run the \command \'pkill -f spring\' to halt all services.
