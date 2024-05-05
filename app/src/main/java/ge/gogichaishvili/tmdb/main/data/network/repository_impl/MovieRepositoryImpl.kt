package ge.gogichaishvili.tmdb.main.data.network.repository_impl

import ge.gogichaishvili.tmdb.main.data.network.dto.MovieResponseDto
import ge.gogichaishvili.tmdb.app.constants.Constants
import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.main.data.network.services.MovieServiceApi
import ge.gogichaishvili.tmdb.main.domain.network.models.MovieResponseUiModel
import ge.gogichaishvili.tmdb.main.domain.network.repository.MovieRepository
import retrofit2.Response
import java.io.IOException

class MovieRepositoryImpl(private val movieServiceApi: MovieServiceApi) : MovieRepository {

    override suspend fun getPopularMoviesRequest(page: Int): Resource<MovieResponseUiModel> {
        return makeSafeApiCall { movieServiceApi.getPopularMovies(Constants.API_KEY, page) }
    }

    override suspend fun searchMoviesRequest(
        query: String,
        page: Int
    ): Resource<MovieResponseUiModel> {
        return makeSafeApiCall { movieServiceApi.searchMovies(Constants.API_KEY, query, page) }
    }

    private suspend fun makeSafeApiCall(call: suspend () -> Response<MovieResponseDto>): Resource<MovieResponseUiModel> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.toDomainModel()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("No content found", null)
            } else {
                Resource.Error(
                    "Error fetching data: ${response.code()} ${response.message()}",
                    null
                )
            }
        } catch (e: IOException) {
            Resource.Error("Network error occurred: ${e.message}", null)
        } catch (e: Exception) {
            Resource.Error("Unknown error: ${e.message}", null)
        }
    }
}

