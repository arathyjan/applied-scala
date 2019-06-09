## GET movies/id

We will now add an endpoint to fetch a movie.

### 1. `PostgresRepository`

You are provided a `PostgresqlRepository` that contains all the SQL needed to work with Postgres. We have done this for you so that you do not have to write any of the SQL on your own.
 
We are using a library called `Doobie`. If we look at this file, we have one function for each of our endpoints already implemented. Keep in mind that the return type of each function is an `IO`.

The function that is useful for us for this endpoint is `fetchMovie`, which has type signature of `MovieId => IO[Option[Movie]]`.

### 2. `FetchMovieService` (exercise)

The `Service` typically has business logic. For example, it may call multiple repositories and then validate their responses to construct another response.

Pay attention to the types we are using in the `Service`. We have a `MovieId` (not a `Long`!) and we are returning a `Movie`. We want nice readable types in our `Service` layer.

There's nothing to do with HTTP or Json responses here.

_**Complete exercise**_

_**Run unit test: `FetchMovieServiceSpec`**_

### 3. `FetchMovieController` (exercise)

The `fetch` method has a `movieId` passed in as a `Long`. This is the id that is in the path of the request `GET movie/123`. The return type is `IO[Response[IO]]`. This is Http4s' response type. 

The `Controller` layer takes care of HTTP request and response so the `Service` layer does not need to.

To complete this exercise, you need to complete the following first:

- Encoder for `Review`
- Encoder for `Movie`

_**Complete exercise**_

_**Run unit test: `FetchMovieControllerSpec`**_

### 4. Wire it all up in `AppRuntime` (exercise)

Now let's wire the `Service` and `Controller` up in `AppRuntime`.

Pass the newly instantiated `fetchMovieController` into `AppRoutes`.

### 5. Update `AppRoutes` (exercise)

Change `AppRoutes` to accept a `FetchMovieController` and then call `fetchMovie` with the `id` extracted out using `LongVar`.

`LongVar` is an extractor. If a `Long` matches in that spot, we get access to it on the right hand side.

### 6. Manual test

Start the app using `./auto/start-local` and `curl http://localhost:9200/movies/1` to get `Titanic` back!