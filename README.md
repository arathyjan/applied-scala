# applied-scala

## Scope

### Day 1

- Introduction
  - Use case. What are we building?
  - Architecture and software components
- Http4s
  - IO
  - Partial functions
  - Kleisli to explain `HttpService`
  - Routing
  - Error handling
- Circe
  - Implicits
  - Manual codecs
  - Auto-derivation
- Testing
  - Codec laws
  - Full end-to-end test

### Day 2

- Doobie
  - Flyway (do it for them)
  - Setting up data (do it for them)
- Deployment (do it for them)
  - What AWS account?
  
## Test queries

```
$ curl -H "Accept: application/json"  -X POST -d "{\"name\": \"\", \"synopsis\": \"\"}" http://localhost:8080/movies
$ curl http://localhost:8080/movies/123
$ curl -H "Accept: application/json"  -X POST -d "[{\"author\": \"Jack\", \"comment\": \"Great movie huh\"}, {\"author\": \"Bob\", \"comment\": \"\"}]" http://localhost:8080/movies/1/reviews
```