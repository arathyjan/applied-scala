## GET movies/id/?enriched=true

Let's add an endpoint to fetch a movie, which is enriched with star rating info from the OMDB API.

### 1. Intro to OMDB

Go to [http://www.omdbapi.com/](http://www.omdbapi.com/), scroll down to _By ID or Title_.

We are going to search for a movie using the `?t` query parameter. If we go to [http://www.omdbapi.com/?t=Titanic&apikey=7f9b5b06](http://www.omdbapi.com/?t=Titanic&apikey=7f9b5b06), we will see the following response:

```
{
  Title: "Titanic",
  Year: "1997",
  Rated: "PG-13",
  Released: "19 Dec 1997",
  Metascore: "75"
  ...
  ...
  ...
}
```

We want to access the `Metascore` and turn it into a `StarRating`. For our purpose, if the score is between 0 and 20, we return `One`, etc.

### 2. `StarRating` - fromScore (exercise)

`StarRating` is an algebraic data type (ADT) that represents all the possible star ratings. 

Let's implement `fromScore`, which we will use to convert a `Metascore` to a `StarRating`. Remember, if the `Int` is outside of 0-100, we want to return `None`.

_**Complete exercise**_

_**Run unit test: `StarRatingSpec`**_

### 3. `StarRating` - decoder (exercise)

We can now implement a `Decoder` instance to convert a `Json` response from OMDB to a `StarRating` using `fromScore`.

If we get a `None` instead of `Some(starRating)`, we want to report a `Left(DecodingFailure(..))`.

The `c.history` that we pass into the `DecodingFailure` contains the path that the cursor has taken to get to this error. It can be useful when you are trying to debug your decoding failures.

_**Complete exercise**_

_**Run unit test: `StarRatingSpec`**_

### 4. `Http4sStarRatingRepository` (exercise)

This has mostly been implemented for you. We use Http4s' `Uri` type to encode the `movieName` so spaces become `%20`, for instance, and then we make a request using an Http4s HTTP Client. 

Hint: We want to start by converting the `String` in the response body into a `StarRating`. For the purpose of this exercise, let's convert any failures from Circe into a `None`.

_**Complete exercise**_

### 5. `FetchEnrichedMovieService` (exercise)

Moving on to the `Service`, we can see it has access to _two_ functions. The first one is to fetch a `Movie` and the second is to fetch a `StarRating`. More concretely, the first is the Postgresql database call and the second one is the OMDB API call.

For the purpose of this exercise, if we get no `StarRating`, we want to error.

_**Complete exercise**_

_**Run unit test: `FetchEnrichedMovieServiceSpec`**_

### 6. `FetchEnrichedMovieController` and `Encoder[EnrichedMovie]` (exercise)

Again, this is not much different than what we've seen. If we have `Some(enrichedMovie)` we want to return `Ok(...)`. If we have `None`, we want to return `NotFound()`. If we have a `Left`, we want to call the `ErrorHandler`.

If you try to convert an `EnrichedMovie` to `Json` using `.asJson`, you will get a compilation error. This is because we have not created the `Encoder` instance for `EnrichedMovie`.

Work on the `Controller` and also the `Encoder`. You will have to create your own custom encoder this time because we want to return a flat `Json`, even though `EnrichedMovie` is a nested structure.

_**Complete exercise**_

_**Run unit test: `FetchEnrichedMovieControllerSpec`**_

### 7. Wire it all up in `AppRuntime` and `AppRoutes`

In `AppRuntime` we can now instantiate our `FetchEnrichedMovieService` and `FetchEnrichedMovieController`, and pass the `Controller` into `AppRoutes`

Finally, we can extend `AppRoutes` to accept `FetchEnrichedMovieController` into the constructor.