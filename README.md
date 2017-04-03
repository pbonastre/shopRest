Usage

Running the Service

./gradlew bootRun # use 'gradlew bootRun' on Windows

Distributing the Service
./gradlew build # use 'gradlew build' on Windows

generates the binary ./build/libs/XXXXX.jar

A Dockerfile is available to build an image. The image build process can be launched using

./gradlew buildDocker # use 'gradlew buildDocker' on windows`

Decision made

Geolocation
To obtain the geolocation for the shops have been resolve using the GMaps Api.

Persistence
To store data from the created shops, I have chosen the Hazelcast approach which eases horizontal scaling. I use an IMap sinchronized in the cluster with name of the shop as key and we insert longitude and latitude when the shop is added. 

Positioning
The distance between the different shops has been resolved using a formula called Haversine, this formula gives you the distance between two geo-coordinates. 
We will iterate for each one of the stored shops and compare to the user location, giving the nearest shop.

Deployment
Spring Boot allows several options, for development it is easy to use the embedded tomcat/jetty/undertown server, but for production you can remove those dependencies and deploy it as a war file in a web server.
A Dockerfile is provided in case you want to build a containerized application.

Testing
Integration test are done in a real test environment created by Spring Boot at runtime.
Rest-assured (http://rest-assured.io/),  have been selected to implement the tests. It offers a  Java DSL for easy testing of REST services.

Expanding this approach, the rest-assured tests can be migrated to BDD style using Serenity-BDD framework (http://www.thucydides.info). Furthermore It could be integrated in a continuous delivery pipeline in the functional tests phase.

