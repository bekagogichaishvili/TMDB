package ge.gogichaishvili.tmdb.main.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.app.tools.SingleLiveEvent
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel
import ge.gogichaishvili.tmdb.main.domain.local.usecase.InsertMovieUseCase
import ge.gogichaishvili.tmdb.main.domain.network.models.MovieDetailsUiModel
import ge.gogichaishvili.tmdb.main.domain.network.usecase.MovieDetailsUseCase
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val insertMovieUseCase: InsertMovieUseCase
) : BaseViewModel() {

    private val _requestStateLiveData =
        SingleLiveEvent<Resource<MovieDetailsUiModel>>()
    val requestStateLiveData: LiveData<Resource<MovieDetailsUiModel>> get() = _requestStateLiveData

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _requestStateLiveData.postValue(Resource.Loading())
            val result =
                movieDetailsUseCase.getMovieDetails(movieId)

            if (result.isSuccess) {
                _requestStateLiveData.postValue(Resource.Success(data = result.data!!))
            } else {
                _requestStateLiveData.postValue(
                    Resource.Error(
                        message = result.message ?: "",
                        errorCode = result.errorCode ?: ""
                    )
                )
            }
        }
    }

    private val _statusMessage = MutableLiveData<Boolean>()
    val statusMessage: LiveData<Boolean> = _statusMessage

    fun insertMovie(movie: FavoriteMovieModel) {
        viewModelScope.launch {
            val success = insertMovieUseCase.execute(movie)
            _statusMessage.value = success
        }
    }

}




