#!/bin/bash

echo -------------------------
echo Starting to compile services..
echo -------------------------
echo This will take a couple of minutes the first time, depending on
echo the download speed.
sleep 2

cd registration-service
mvn package
cd target
mv spring-registration-service-1.0.0-SNAPSHOT.jar ../../
cd ..
cd ..

cd edge-service
mvn package
cd target
mv spring-edge-service-1.0.0-SNAPSHOT.jar ../../
cd ..
cd ..

cd comment-service
mvn package
cd target
mv spring-comment-service-1.0.0-SNAPSHOT.jar ../../
cd ..
cd ..

cd image-service
mvn package
cd target
mv spring-image-service-1.0.0-SNAPSHOT.jar ../../
cd ..
cd ..

cd user-service
mvn package
cd target
mv spring-user-service-1.0.0-SNAPSHOT.jar ../../
cd ..
cd ..

cd web-service
mvn package
cd target
mv spring-web-service-1.0.0-SNAPSHOT.jar ../../
cd ..
cd ..

echo -------------------------
echo DONE!
echo Run \'runServices.sh\' to launch services.
echo -------------------------
