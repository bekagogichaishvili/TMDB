package ge.gogichaishvili.tmdb.main.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ge.gogichaishvili.tmdb.R
import ge.gogichaishvili.tmdb.app.network.ApiEndpoints
import ge.gogichaishvili.tmdb.databinding.LayoutMovieItemBinding
import ge.gogichaishvili.tmdb.main.domain.network.models.MovieUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesAdapter() : PagingDataAdapter<MovieUiModel, MoviesAdapter.ViewHolder>(differCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutMovieItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(getItem(position)!!)
    }

    inner class ViewHolder(
        private val binding: LayoutMovieItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieUiModel) {
            binding.tvMovieName.text = item.title
            binding.tvMovieReleaseDate.text = item.releaseDate
            binding.tvMovieDescription.text = item.overview

            Glide.with(context)
                .load(ApiEndpoints.IMAGE_PATH + item.posterPath)
                .error(R.drawable.no_poster)
                .centerCrop()
                .into(binding.ivPoster)

            binding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(item)
                }
            }
        }
    }

    private var onItemClickListener: ((MovieUiModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieUiModel) -> Unit) {
        onItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    suspend fun clear() {
        withContext(Dispatchers.Main) {
            submitData(PagingData.empty())
            notifyDataSetChanged()
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<MovieUiModel>() {
            override fun areItemsTheSame(
                oldItem: MovieUiModel,
                newItem: MovieUiModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MovieUiModel,
                newItem: MovieUiModel
            ): Boolean {
                return oldItem.title == newItem.title && oldItem.id == newItem.id
            }
        }
    }
}
