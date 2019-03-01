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

## Scope

### Day 1

| Time | Topic/Exercise | Presenter | Helper(s)
| :---: | :---: | :--: | :--: |
|  9.15 | Day 1 Keynote | Chris |
|  9.45 | Introduction to Cats | Chris | 
| 10.15 | Morning tea | 
| 10.30 | IO Exercises | Felipe | Chris, Jack
| 11.15 | Http4s overview + Endpoint 1: Hello World | Felipe | Chris, Jack
| 12.15 | Lunch | 
| 13.30 | Circe Exercises | Stili | Felipe, Adam
| 14.15 | Endpoint 2: GET movie | Stili | Felipe, Adam
| 15.15 | Afternoon tea |
| 15.30 | Endpoint 3: Diagnostics | Adam | Stili, Jack
| 16.00 | Deploy to AWS | Adam | Stili, Jack
| 17.00 | End |

### Day 2 

| Time | Topic/Exercise | Presenter | Helper(s)
| :---: | :---: | :--: | :-:
|  9.15 | Day 2 Keynote | Jake
|  9.45 | Endpoint 3: GET movie?enriched=true | Jack | Adam, Chris
| 10.45 | Morning tea | 
| 11.00 | Validated and Traverse Theory | Chris | Jack, Adam
| 11.45 | Validated Exercises | Chris | Jack, Adam
| 12.30 | Lunch |
| 13.45 | Endpoint 4: POST movie | Jake | Chris, Jack
| 14.30 | Afternoon tea | 
| 15.00 | Endpoint 5: POST movie/id/review | Jake | Chris, Jack
| 16:00 | Wrap-up | Jake
| 16:30 | End |

## Test queries

```
$ curl -H "Accept: application/json"  -X POST -d "{\"name\": \"\", \"synopsis\": \"\"}" http://localhost:9200/movies
$ curl http://localhost:9200/movies/1?enriched=true
$ curl -H "Accept: application/json"  -X POST -d "[{\"author\": \"Jack\", \"comment\": \"Great movie huh\"}, {\"author\": \"Bob\", \"comment\": \"\"}]" http://localhost:9200/movies/1/reviews
```
