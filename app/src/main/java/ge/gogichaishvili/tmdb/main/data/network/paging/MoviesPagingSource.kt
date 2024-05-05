package ge.gogichaishvili.tmdb.main.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.main.domain.models.MovieUiModel
import ge.gogichaishvili.tmdb.main.domain.usecase.GetPopularMoviesUseCase
import ge.gogichaishvili.tmdb.main.domain.usecase.SearchMoviesUseCase
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val query: String? = null
) : PagingSource<Int, MovieUiModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieUiModel> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        val pageSize = params.loadSize

        return try {
            val resource = if (query.isNullOrEmpty()) {
                getPopularMoviesUseCase.execute(currentPage)
            } else {
                searchMoviesUseCase.execute(query, currentPage)
            }

            when (resource) {
                is Resource.Success -> {
                    val movies = resource.data?.results?.map { it.toDomainModel() } ?: emptyList()
                    val nextKey = if (movies.size < pageSize) null else currentPage + 1
                    val prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1
                    LoadResult.Page(
                        data = movies,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                is Resource.Error -> LoadResult.Error(
                    IOException(
                        resource.message ?: "Unknown error"
                    )
                )

                else -> LoadResult.Error(Exception("Unhandled state in resource"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieUiModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { page ->
                page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
            }
        }
    }
}
