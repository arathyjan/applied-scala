# applied-scala

## Scope

### Trial

| Time | Topic/Exercise |
| :---: | :---: | 
| 10.00 | Intro to Applied Scala |
| 10.30 | IO Exercises |
| 11.00 | Circe Exercises |
| 11.30 | Http4s Overview |
| 12.00 | Lunch |
| 13.15 | Endpoint 1: Hello World |
| 13.30 | Endpoint 2: GET movie |
| 14.00 | Endpoint 3: GET movie?enriched=true |
| 14.30 | Validated Theory + Exercises |
| 14.50 | Validated Exercises |
| 15.15 | Traverse Theory |
| 15.30 | Endpoint 4: POST movie |
| 16.00 | Endpoint 5: POST movie/id/review |
| 16:30 | End |

#### Intro to Applied Scala

- What are we building?
- Architectural and software components
- Design decisions
- We want to write pure functional programs
- No undeclared side-effects
- Referentially transparent programs
- Single responsiblity, module boundaries

#### Http4s Overview

- Why Http4s?
- Lightweight container, not heavily opinionated
- (Maybe) Motivation behind the Http4s DSL, how it works


#### Hello World

- `AppServer`
- `AppRoutes` to explain routing
- `Controller`, `Service` and `AppRuntime`

#### GET movie

- Handhold through exercise
- Show `PostgresRepository`
- Complete `GetMovieService` to pass test
- Complete `GetMovieController`
- Complete semi-auto encoders
- Wire everything up in `AppRuntime`

#### GET movie?enriched=true

- Handwave query param matcher
- Show OMDB API
- Handhold implementation of `Http4sStarRatingRepository`
- Get them to complete the rest
- Cover custom encoders

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


## How to start app

```
./auto/start-local
```

## Test queries

```
$ curl -H "Accept: application/json"  -X POST -d "{\"name\": \"\", \"synopsis\": \"\"}" http://localhost:9200/movies
$ curl http://localhost:9200/movies/1?enriched=true
$ curl -H "Accept: application/json"  -X POST -d "[{\"author\": \"Jack\", \"comment\": \"Great movie huh\"}, {\"author\": \"Bob\", \"comment\": \"\"}]" http://localhost:9200/movies/1/reviews
```
