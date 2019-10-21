# user-auth-service
A small microservice with authorization and authentication capabilities

Basic instructions - 
1. Any API except user creation and login need a token to acess them
2. One first needs to first create an account for them using create used (apart from any default or admin ones if already created)
3. For getting a token, one needs to first login and get the token
4. After getting a token, one needs to pass the "userId" and "token" as headers for subsequent requests to access other APIs.
5. TODO - write about all other APIs and functionalities afterwards.

Build and Run Instructions -
1. ./gradlew build
2. java -jar build/libs/user-authenticator-0.0.1-SNAPSHOT.jar
