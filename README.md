# Secure JTW

Basic application to secure REST APIs with JWT.

##Project versions:
 - Java 17
 - Spring boot 3.0.0
 

##This project has 3 REST endpoints:

 - POST `http://localhost:8080/authenticate` - Accepts the following JSON information:
    ```
   {
    "username": "user",
    "password": "password"
   }
   ```
    Which authenticates the user against an in-memory-user and returns a bearer token, which is accepted by:
 - GET `http://localhost:8080/authenticate` - returns "Hello user" if the user has a bearer token. Otherwise returns 401.<br>
 - GET `http://localhost:8080/authenticate` - returns user roles if user has a bearer token. Otherwise returns 401