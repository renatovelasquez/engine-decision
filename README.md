# engine-decision

This application provides a decision engine which takes in personal code, loan amount, loan period in months and returns
a decision (negative or positive) and the amount.

-----

## Requirements:

- Docker Compose
- Currently, only tested on Fedora 39 and IntelliJ IDEA

## How to run:

- `docker-compose -f docker-compose.yml up`

## API Pages (URLs):

- http://localhost:3000 engine decision form

## API Pages (URLs):

- http://localhost:8080/api/engine endpoint engine decision.
- http://localhost:8080/h2/ to see H2 management.
- http://localhost:8080/swagger-ui/index.html to see a Swagger-UI formatted documentation.

Note that, in the API documentation pages, documentations for POST requests for the engine decision can be seen.
Moreover, in Swagger UI for example, making a query is quite easy with example formats.

## License

engine-decision is distributed under the terms of the
[MIT License](https://choosealicense.com/licenses/mit).