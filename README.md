# applied-scala

## How to start app

```
./auto/start-local
```

## Scope

### Trial

| Time | Topic/Exercise | Presenter
| :---: | :---: | :--:
| 10.00 | Intro to Applied Scala | Chris
| 10.30 | IO Exercises | Jack
| 11.00 | Circe Exercises | Jack
| 11.30 | Http4s Overview | Chris
| 12.00 | Lunch |
| 13.15 | Endpoint 1: Hello World | Jack
| 13.30 | Endpoint 2: GET movie | Jack
| 14.00 | Endpoint 3: GET movie?enriched=true | Jack
| 14.30 | Validated and Traverse Theory | Chris
| 15.00 | Validated Exercises | Jack
| 15.30 | Endpoint 4: POST movie | Jack
| 16.00 | Endpoint 5: POST movie/id/review | Jack
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

#### GET movies/id

- Handhold through exercise
- Show `PostgresRepository`
- Complete `GetMovieService` to pass test
- Complete `GetMovieController`
- Complete semi-auto encoders
- Wire everything up in `AppRuntime`

#### GET movies/id/?enriched=true

- Handwave query param matcher
- Show OMDB API
- Handhold implementation of `Http4sStarRatingRepository`
- Get them to complete the rest
- Cover custom encoders

#### POST movies

- Complete `NewMovieValidator`
- Complete `SaveMovieService` using `Validated` and `Traverse`
- Show how to decode request in `SaveMovieController`
- Custom encoder for `MovieId`

#### POST movies/id/review

- Similar to above

## Test queries

```
$ curl -H "Accept: application/json"  -X POST -d "{\"name\": \"\", \"synopsis\": \"\"}" http://localhost:9200/movies
$ curl http://localhost:9200/movies/1?enriched=true
$ curl -H "Accept: application/json"  -X POST -d "[{\"author\": \"Jack\", \"comment\": \"Great movie huh\"}, {\"author\": \"Bob\", \"comment\": \"\"}]" http://localhost:9200/movies/1/reviews
```
