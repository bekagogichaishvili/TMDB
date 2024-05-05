package ge.gogichaishvili.tmdb.main.domain.network.usecase

import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.main.domain.network.models.MovieDetailsUiModel
import ge.gogichaishvili.tmdb.main.domain.network.repository.MovieDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsUseCase(private val movieDetailsRepository: MovieDetailsRepository) {
    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetailsUiModel> =
        withContext(Dispatchers.IO) {
            val result =
                movieDetailsRepository.getMovieDetailsRequest(movieId)
            if (result.isSuccess) {
                Resource.Success(result.data!!)
            } else {
                Resource.Error(
                    message = result.message ?: "",
                    errorCode = (result as Resource.Error).errorCode
                )
            }
        }
}