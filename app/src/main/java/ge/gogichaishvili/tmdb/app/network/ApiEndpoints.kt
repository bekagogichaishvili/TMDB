package ge.gogichaishvili.tmdb.app.network

object ApiEndpoints {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_PATH = "https://image.tmdb.org/t/p/w500"

    //Main
    const val GET_POPULAR_MOVIES = "movie/popular"
    const val SEARCH_MOVIES = "search/movie"
    const val MOVIE_DETAILS = "movie"

    //Login
    private const val TOKEN = "authentication/token"
    const val NEW = "$TOKEN/new"
    const val LOGIN = "$TOKEN/validate_with_login"

}
