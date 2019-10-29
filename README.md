# towns

The app serves as a database for towns. 
It provides the available ones, and registered users can add more towns, 
as well as select their favourite ones.


## Run

To run on 8090 with the wrapper
```
./mvnw spring-boot:run
```

TODO JEL IMA SWAGG
Swagger is accessible on ```localhost:8090/swagger-ui.html```.


## Usage


Available towns can be listed sorted by their creation date, 
or by their popularity (how many users choose them as their favourite ones).
```
curl -i -X GET localhost:8090/towns/byCreated 
curl -i -X GET localhost:8090/towns/byPopularity 
```


User must be registered and authenticated to add towns and to select their favourite ones.

To register with credentials _tester_/_tester_:
```
curl -i -X POST localhost:8090/register -H "Content-Type: application/json" -d "{ \"username\": \"tester\", \"password\": \"tester\"}"
```

To authenticate the existing user _tester_/_tester_:
```
curl -i -X POST localhost:8090/authenticate -H "Content-Type: application/json" -d "{ \"username\": \"tester\", \"password\": \"tester\"}"
```

Both the registration and the authentication will return a token that must be included for secured endpoints.

The response would be in format:
```
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNTcyMzE3NDM5LCJpYXQiOjE1NzIyOTk0Mzl9.L3jOVNJ8UOnDD2Neznh-yDZYbjIbVs7oFQoIRy9aCQaoOe0CO6ar5N33B0vqlDAdsY86-jjhpGxQ8fyW8ihTwQ"
}
```

To add additional towns, where <token> is a valid token:
```
curl -i -X POST localhost:8090/users/town -H "Content-Type: application/json" -H "Authorization: Bearer <token>" \
-d "{ \"name\": \"Hum\", \"description\": \"najmanji grad\", \"population\": 100}"
```

To add a town as favourite, where <town_id> of the favourite town (can be obtained by listing the towns):
```
curl -i -X POST localhost:8090/users/towns/<town_id> -H "Content-Type: application/json" -H "Authorization: Bearer <token>"
```

To remove it as favourite:
```
curl -i -X DELETE localhost:8090/users/towns/<town_id> -H "Content-Type: application/json" -H "Authorization: Bearer <token>"
```
