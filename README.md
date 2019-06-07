# applied-scala

[![Build Status](https://badge.buildkite.com/17c1e11361daf6a504a721d6b280306789cd81a0a77cad7fc3.svg)](https://buildkite.com/rea/applied-scala)

## Getting Started

Similar to [Intro to Scala](https://github.com/wjlow/intro-to-scala#pre-requisites)

1. Skip this step if you have already done Intro to Scala on your current machine. If you're going to use [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Community edition is fine), you need to install Java 8 even if you have a newer version of Java installed.

```
brew tap caskroom/versions
brew cask install caskroom/versions/adoptopenjdk8
```

2. Before the course, please run the following:

```
git clone git@git.realestate.com.au:scala-course/applied-scala.git
./auto/test 
./auto/start-local
```

This should start the app.

3. Now test this out in a new tab.

```
curl http://localhost:9200/movies
```

You should get back `[{"name":"Titanic"}]`. Now press `ctrl+c` in the previous tab to shut down the app.

4. Open up the project in IntelliJ IDEA and make sure it all compiles. Now you're ready to go!

## Open up SBT

Using Docker
```
./auto/sbt
```

or

Using portable SBT 
```
./sbt
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

### Day 1 (11/6)

| Time | Topic/Exercise |
| :---: | :---: | 
|  9.15 | Day 1 Keynote |
|  9.45 | Introduction to Cats |
| 10.00 | Morning break | 
| 10.15 | IO Exercises | 
| 11.15 | Http4s overview + Endpoint 1: Hello World |
| 12.00 | Catered lunch | 
| 13.00 | Circe Exercises | Stili  |
| 14.00 | Code walkthrough: GET all movies (no exercises) |
| 14.30 | Endpoint 2: GET movie |
| 15.30 | Afternoon break |
| 15.45 | Endpoint 3: Diagnostics |
| 16.45 | Demonstration: Deploy to AWS |
| 17.00 | End |

### Day 2 (12/6)

| Time | Topic/Exercise |
| :---: | :---: |
|  9.15 | Day 2 Keynote |
| 10.00 | Endpoint 4: GET movie?enriched=true |
| 10.30 | Morning break | 
| 10.45 | Endpoint 4 (continued) |
| 11.15 | Validated and Traverse Theory |
| 12.00 | Catered lunch |
| 13.00 | Validated Exercises | 
| 14.00 | Endpoint 5: POST movie |
| 15.15 | Afternoon break | 
| 15.30 | Endpoint 6: POST movie/id/review |
| 16:45 | Wrap-up | Jake
| 17:00 | End |

## Further reading

[Wiki](https://git.realestate.com.au/scala-course/applied-scala/wiki/Further-reading)

## Test queries

Fetch all movies
```
$ curl http://localhost:9200/movies
```

Fetch movie
```
$ curl http://localhost:9200/movies/1
```

Fetch enriched movie

```
$ curl http://localhost:9200/movies/1?enriched=true
```

Save movie

1. Successful save
```
$ curl -H "Accept: application/json" -X POST -d "{\"name\": \"Cars 3\", \"synopsis\": \"Great movie about cars\"}" http://localhost:9200/movies
```

2. Validation errors
```
$ curl -H "Accept: application/json" -X POST -d "{\"name\": \"\", \"synopsis\": \"\"}" http://localhost:9200/movies
```

Save review

1. Successful save
```
$ curl -H "Accept: application/json"  -X POST -d "{\"author\": \"Jack\", \"comment\": \"Great movie huh\"}" http://localhost:9200/movies/1/reviews
```

2. Validation errors

```
$ curl -H "Accept: application/json"  -X POST -d "[{\"author\": \"\", \"comment\": \"\"}, {\"author\": \"Bob\", \"comment\": \"\"}]" http://localhost:9200/movies/1/reviews
```
