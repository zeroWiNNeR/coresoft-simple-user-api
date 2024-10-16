# coresoft-simple-user-api


### _user-service_ [SwaggerUi](http://localhost:8081/api/swagger-ui/swagger-ui/index.html)
Cервис управления пользователями.  
Необходимый набор переменных:
```
server.port=8081
DB_HOST=localhost
DB_PORT=5432
DB_NAME=coresoft-user-service
DB_SCHEMA=public
DB_USERNAME=
DB_PASSWORD=
FEIGN_ROLE_SERVICE_URL=http://localhost:8082
```

### _role-service_ [SwaggerUi](http://localhost:8082/api/swagger-ui/swagger-ui/index.html)
Cервис управления ролями пользователей.  
Необходимый набор переменных:
```
server.port=8082
DB_HOST=localhost
DB_PORT=5432
DB_NAME=coresoft-role-service
DB_SCHEMA=public
DB_USERNAME=
DB_PASSWORD=
```

### _gateway-service_  
Точка входа внешних запросов в приложение. Сервис выполняет BasicAuth, присваивает request-id заголовок для логов, затем маршрутизирует запрос к конечному сервису.  
Необходимый набор переменных:
```
USER_SERVICE_URL=http://localhost:8081
ROLE_SERVICE_URL=http://localhost:8082
```