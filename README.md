CookieSessionStore Reproducer
==============================

1. Run Service
2. Attempt to authenticate via POST http://localhost:8080/authenticate
   3. Send a Post Request with this payload:
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
