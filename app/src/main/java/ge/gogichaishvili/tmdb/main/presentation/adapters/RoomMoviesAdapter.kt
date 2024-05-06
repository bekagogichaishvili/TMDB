package ge.gogichaishvili.tmdb.main.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ge.gogichaishvili.tmdb.databinding.LayoutRoomItemBinding
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomMoviesAdapter :
    PagingDataAdapter<FavoriteMovieModel, RoomMoviesAdapter.ViewHolder>(differCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoomMoviesAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRoomItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: RoomMoviesAdapter.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(getItem(position)!!)
    }

    inner class ViewHolder(
        private val binding: LayoutRoomItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteMovieModel) {
            binding.textView.text = item.title

            /*  Glide.with(context)
                  .load(ApiEndpoints.IMAGE_PATH + item.posterPath)
                  .error(R.drawable.no_poster)
                  .centerCrop()
                  .into(binding.ivPoster)*/

            binding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(item)
                }
            }
        }
    }

    private var onItemClickListener: ((FavoriteMovieModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (FavoriteMovieModel) -> Unit) {
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
        val differCallback = object : DiffUtil.ItemCallback<FavoriteMovieModel>() {
            override fun areItemsTheSame(
                oldItem: FavoriteMovieModel,
                newItem: FavoriteMovieModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FavoriteMovieModel,
                newItem: FavoriteMovieModel
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}