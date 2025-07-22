CookieSessionStore Reproducer
==============================

1. Run Service
2. Attempt to authenticate via POST http://localhost:8080/authenticate
   - Send a Post Request with this payload:
     ```
     {
         "username": "myuser"
     }
     ```
3. Receive a 200 with a set-cookie
4. Attempt to call GET http://localhost:8080/private/whoami
5. Receive a 401 Unauthorized
6. Authenticate again with http://localhost:8080/authenticate
7. Receive another 200 with a set-cookie
8. Attempt to call GET http://localhost:8080/private/whoami
9. Receive a 200 with a response
10. Change to `LocalSessionStore` by switching lines 28/29 in the `MainVerticle`
11. Authentication now works as expected with only one call needed to authenticate.


## Requests

### CookieSessionStore
1. Initial Authentication

Returns 200 with set-cookie
```
curl -v --location --request POST 'http://localhost:8080/authenticate' --header 'Content-Type: application/json' --data '{"username":"myuser"}'
```
2. Initial Attempt at accessing. Replace cookie with set-cookie value from previous call.

Returns 401 
```
curl -v --location --request GET 'http://localhost:8080/private/whoami' --header 'Cookie: vertx-web.session=someCookie'
```
3. Second Authentication. Replace cookie with set-cookie value initial authentication.

Returns 200 with set-cookie
```
curl -v --location --request POST 'http://localhost:8080/authenticate' --header 'Content-Type: application/json' --header 'Cookie: vertx-web.session=someCookie' --data '{"username":"myuser"}'
```
4. Second Attempt at accessing. Replace cookie with set-cookie value from second authentication call.

Returns 200 with user
```
curl -v --location --request GET 'http://localhost:8080/private/whoami' --header 'Cookie: vertx-web.session=anotherCookie'
```

### LocalSessionStore
1. Initial Authentication

Returns 200 with set-cookie
```
curl -v --location --request POST 'http://localhost:8080/authenticate' --header 'Content-Type: application/json' --data '{"username":"myuser"}'
```
2. Initial Attempt at accessing. Replace cookie with set-cookie value from previous call.

Returns 200 with user
```
curl -v --location --request GET 'http://localhost:8080/private/whoami' --header 'Cookie: vertx-web.session=someCookie'
