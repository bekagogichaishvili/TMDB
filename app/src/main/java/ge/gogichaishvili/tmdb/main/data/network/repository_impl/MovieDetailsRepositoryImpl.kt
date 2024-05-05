package ge.gogichaishvili.tmdb.main.data.network.repository_impl

import ge.gogichaishvili.tmdb.app.constants.Constants
import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.app.network.getErrorExceptionMessage
import ge.gogichaishvili.tmdb.main.data.network.services.MovieDetailsServiceApi
import ge.gogichaishvili.tmdb.main.domain.models.MovieDetailsUiModel
import ge.gogichaishvili.tmdb.main.domain.repository.MovieDetailsRepository
import retrofit2.HttpException

class MovieDetailsRepositoryImpl(
    private val movieDetailsServiceApi: MovieDetailsServiceApi
) : MovieDetailsRepository {

    override suspend fun getMovieDetailsRequest(movieId: Int): Resource<MovieDetailsUiModel> {
        return try {
            val response = movieDetailsServiceApi.getMovieDetails(movieId, Constants.API_KEY)

            return if (response.isSuccessful) {

                val result = response.body()!!.toDomainModel()

                Resource.Success(data = result)
            } else {
                Resource.Error(message = response.errorBody()?.string() ?: "")
            }

        } catch (e: Exception) {
            val errorMessage = (e as? HttpException)?.getErrorExceptionMessage()
            Resource.Error(message = errorMessage ?: e.message ?: "")
        }
    }
}