# RateLogMiddleware

RateLogMiddleware is a Spring Boot project that implements middleware for logging HTTP requests and responses, along with a rate limiter to restrict requests to a maximum of 3 within 10 seconds per IP.

## Features

- **Logging Middleware**: Logs incoming HTTP requests and responses, including timestamp, IP address, request method, and status.
- **Rate Limiting Middleware**: Limits the number of requests to 3 per 10 seconds per IP address, returning a 429 status if exceeded.
- **Centralized Logging**: Logs can be directed to the console, a file, or a database (extensible).
- **Easy Integration**: Can be added to any Spring Boot project as an interceptor.
