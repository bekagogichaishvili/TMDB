package ge.gogichaishvili.tmdb.main.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ge.gogichaishvili.tmdb.R
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

        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: FavoriteMovieModel) {
            binding.tvTitle.text = item.title
            binding.tvInfo.text = item.overview
            val imageData: ByteArray = item.image
            val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
            Glide.with(context)
                .load(bitmap)
                .error(R.drawable.no_poster)
                .centerCrop()
                .into(binding.ivPoster)

            binding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(item)
                }
            }
            binding.ibDelete.setOnClickListener {
                onDeleteClickListener?.let { click ->
                    click(item)
                    refresh()
                    notifyDataSetChanged()

                }
            }
        }
    }

    private var onItemClickListener: ((FavoriteMovieModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (FavoriteMovieModel) -> Unit) {
        onItemClickListener = listener
    }

    private var onDeleteClickListener: ((FavoriteMovieModel) -> Unit)? = null
    fun setOnDeleteClickListener(listener: (FavoriteMovieModel) -> Unit) {
        onDeleteClickListener = listener
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