# engine-backend

This application provides a decision engine which takes in personal code, loan amount, loan period in
months and returns a decision (negative or positive) and the amount.
-----

## Requirements:

- Java 11
- Gradle 8.4
- Docker
- Currently, only tested on Fedora 39 and IntelliJ IDEA

## How to run:

- `cd engine-decision`
- `gradle clean build`
- `gradle bootRun`

## Run with Docker:

### Using the existing JAR file:

- `cd engine-decision`
- `docker build --no-cache --build-arg JAR_FILE=engine-decision.jar -t renvl/engine-decision .`
- `docker run -p 8080:8080 -t renvl/engine-decision`

### Compiling the application:

- `cd engine-decision`
- `gradle clean build`
- `docker build --no-cache --build-arg JAR_FILE=build/libs/\*.jar -t renvl/engine-decision .`
- `docker run -p 8080:8080 -t renvl/engine-decision`

## API Pages (URLs):

- http://localhost:8080/api/engine endpoint engine decision.
- http://localhost:8080/h2/ to see H2 management.
- http://localhost:8080/swagger-ui/index.html to see a Swagger-UI formatted documentation.

Note that, in the API documentation pages, documentations for POST requests for the engine decision can be seen.
Moreover, in Swagger UI for example, making a query is quite easy with example formats.

## Parametrization:

To connect the embedded H2 Database use the parameters according `src/main/resources/application.properties`

## License

engine-decision is distributed under the terms of the
[MIT License](https://choosealicense.com/licenses/mit).