Guide to how to compile and run the services:
---------------------------------------------

1) Go into each service folder, and type 'mvn package' in the terminal. This will
compile the code along with all dependencies into a single JAR file.

2) Run the jar file with the command 'java -jar spring-{NAME}-service-1.0.0-SNAPSHOT.jar'
using the appropriate filename. It might take up to two minutes for the application to
register itself with eureka.

3) Go to localhost:9998 to see all services registered with Eureka.

4) Go to localhost:9999 to see the webapplication.


Dependencies:
-------------

- Java 8
- Maven (https://maven.apache.org/)

All remaining dependencies will be downloaded by Maven.