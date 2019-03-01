# applied-scala

## Open up SBT

```
./auto/dev-environment sbt
```

## Run test

```
./auto/test
```

## How to start app

```
./auto/start-local
```

## Schedule

### Day 1 (28/3)

| Time | Topic/Exercise | 
| :---: | :---: | 
|  9.15 | Day 1 Keynote | 
|  9.45 | Introduction to Cats |
| 10.15 | Morning tea | 
| 10.30 | IO Exercises |
| 11.15 | Http4s overview + Endpoint 1: Hello World |
| 12.15 | Lunch | 
| 13.30 | Circe Exercises | 
| 14.15 | Code walkthrough: GET all movies (no exercises) |
| 14.45 | Endpoint 2: GET movie |
| 15.45 | Afternoon tea |
| 16.00 | Endpoint 3: Diagnostics | 
| 16.30 | Deploy to AWS | 
| 17.00 | End |

### Day 2 (29/3)

| Time | Topic/Exercise |
| :---: | :---: | 
|  9.15 | Day 2 Keynote | 
|  9.45 | Endpoint 4: GET movie?enriched=true |
| 10.45 | Morning tea | 
| 11.00 | Validated and Traverse Theory |
| 11.45 | Validated Exercises | 
| 12.30 | Lunch |
| 13.45 | Endpoint 5: POST movie |
| 14.30 | Afternoon tea | 
| 15.00 | Endpoint 6: POST movie/id/review |
| 16:00 | Wrap-up | 
| 16:30 | End |

## Test queries

```
$ curl -H "Accept: application/json"  -X POST -d "{\"name\": \"\", \"synopsis\": \"\"}" http://localhost:9200/movies
$ curl http://localhost:9200/movies/1?enriched=true
$ curl -H "Accept: application/json"  -X POST -d "[{\"author\": \"Jack\", \"comment\": \"Great movie huh\"}, {\"author\": \"Bob\", \"comment\": \"\"}]" http://localhost:9200/movies/1/reviews
```
