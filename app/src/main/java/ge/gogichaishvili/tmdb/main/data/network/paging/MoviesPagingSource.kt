package ge.gogichaishvili.tmdb.main.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ge.gogichaishvili.tmdb.main.data.network.dto.Movie
import ge.gogichaishvili.tmdb.main.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val repository: MovieRepository,
    private val apiKey: String,
    private val query: String? = null
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            val pageSize = params.loadSize

            val response = if (query.isNullOrEmpty()) {
                repository.getPopularMoviesRequest(apiKey, currentPage)
            } else {
                repository.searchMoviesRequest(apiKey, query, currentPage)
            }

            if (response.isSuccessful) {
                val data = response.body()?.results ?: emptyList()
                val responseData = mutableListOf<Movie>()
                responseData.addAll(data)
                val nextKey = if (responseData.size < pageSize) null else currentPage + 1
                val prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1
                return LoadResult.Page(
                    data = responseData,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                return LoadResult.Error(HttpException(response))
            }

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}