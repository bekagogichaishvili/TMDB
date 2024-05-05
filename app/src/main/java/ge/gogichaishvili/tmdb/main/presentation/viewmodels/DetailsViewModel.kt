package ge.gogichaishvili.tmdb.main.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.app.tools.SingleLiveEvent
import ge.gogichaishvili.tmdb.main.domain.network.models.MovieDetailsUiModel
import ge.gogichaishvili.tmdb.main.domain.network.usecase.MovieDetailsUseCase
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val movieDetailsUseCase: MovieDetailsUseCase) : BaseViewModel() {

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

}