POST http://localhost:8080/api/hello/{text}
GET http://localhost:8080/api/hello/{text}
PUT http://localhost:8080/api/hello/1
PATCH http://localhost:8080/api/hello/{text}
DELETE http://localhost:8080/api/hello/1

http://localhost:8080/actuator/metrics
http://localhost:8080/actuator/info
http://localhost:8080/actuator/health

http://localhost:8080/actuator/metrics/my.counter
http://localhost:8080/actuator/metrics/my.timer
http://localhost:8080/actuator/metrics/my.gauge
http://localhost:8080/actuator/metrics/custom.distribution.summary