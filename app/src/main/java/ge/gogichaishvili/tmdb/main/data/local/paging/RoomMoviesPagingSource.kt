package ge.gogichaishvili.tmdb.main.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel
import ge.gogichaishvili.tmdb.main.domain.local.usecase.GetAllMoviesUseCase
import kotlinx.coroutines.delay

private const val STARTING_PAGE_INDEX = 0

class RoomMoviesPagingSource(
    private val getAllMoviesUseCase: GetAllMoviesUseCase
) : PagingSource<Int, FavoriteMovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteMovieModel> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        val pageSize = params.loadSize
        val offset = currentPage * pageSize

        return try {

            if (currentPage != STARTING_PAGE_INDEX) delay(1000)

            val movies = getAllMoviesUseCase.execute(pageSize, offset)
            val nextKey = if (movies.size < pageSize) null else currentPage + 1
            val prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1

            return LoadResult.Page(
                data = movies,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FavoriteMovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { page ->
                page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
            }
        }
    }
}
