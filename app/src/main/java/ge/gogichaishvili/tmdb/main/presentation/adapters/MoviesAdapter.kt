package ge.gogichaishvili.tmdb.main.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ge.gogichaishvili.tmdb.app.network.ApiEndpoints
import ge.gogichaishvili.tmdb.databinding.LayoutMovieItemBinding
import ge.gogichaishvili.tmdb.main.data.network.dto.Movie

class MoviesAdapter() :
    PagingDataAdapter<Movie, MoviesAdapter.ViewHolder>(
        differCallback
    ) {

    private lateinit var binding: LayoutMovieItemBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = LayoutMovieItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setIsRecyclable(false)
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)

    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Movie) {
            binding.apply {

                tvMovieName.text = item.title

                Glide.with(binding.ivPoster.context)
                    .load(ApiEndpoints.IMAGE_PATH + item.posterPath)
                    .into(binding.ivPoster)

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.title == newItem.title && oldItem.id == newItem.id
            }
        }
    }
}
