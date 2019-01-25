# applied-scala

## Scope

### Day 1

- Keynote (State of REA's Scala apps) (Jake)
- Introduction
  - PSW Scala stencil (Luke)
    - Demo the stencil
    - Switch to Applied Scala app  
  - Use case. What are we building? Architectural and software components? Design decisions?
    - Why Http4s?
    - Lightweight container, not heavily opinionated
    - Allows us to write pure functional programs
    - Not gonna do undeclared side-effects, writing RT software
    - Single responsibility, module boundaries
    - (Maybe) Motivation behind the Http4s DSL, how it works
- Prerequisites
  - IO Exercises
    - map, flatMap, raiseError, attempt
  - Circe Exercises
    - Implicits
    - Manual codecs
    - Auto-derivation
- Implicits
  - Typeclass instances
  - Extension methods
- Http4s
  - Testing (teach as we go)
  - `GET movie/{id}`
    - Show `AppRoutes` to explain routing
    - Build `GET movie/{id}` endpoint to get `Movie` <-- use semi-auto codecs
    - Doobie
    - Extend endpoint to get `EnrichedMovie` <-- use custom codecs
  - `GET movies`
    - No new concepts
  - `POST movie`
    - Validated exercises
    - Accept JSON body and decode into `NewMovieReq`
    - Collect all errors using `IO[MovieId]` return type (if name is empty... if synopsis is empty... if both are empty...) 
    - Teach `Semigroup`, `Validated`, `Traverse` theory (using Exercises)
    - Implement using `ValidatedNel`
  - `POST movie/{id}/review`
    - Probably nothing new here


## Test queries

```
$ curl -H "Accept: application/json"  -X POST -d "{\"name\": \"\", \"synopsis\": \"\"}" http://localhost:8080/movies
$ curl http://localhost:8080/movies/123
$ curl -H "Accept: application/json"  -X POST -d "[{\"author\": \"Jack\", \"comment\": \"Great movie huh\"}, {\"author\": \"Bob\", \"comment\": \"\"}]" http://localhost:8080/movies/1/reviews
```
