# Secure JTW

Basic application to secure REST APIs with JWT tokens through an OAuth2 resource server.

##Project versions:
 - Java 17
 - Spring boot 2.7.3
 

##This project has 2 REST endpoints:

 - POST localhost:8080/authenticate - Accepts the following JSON information:
    ```
   {
    "username": "user",
    "password": "password"
   }
   ```
    Which authenticates the user against an in-memory-user and returns a bearer token, which is accepted by:
 - GET localhost:8080/authenticate - returns "Hello user" if the user has a bearer token. Otherwise returns error 401.<br>
