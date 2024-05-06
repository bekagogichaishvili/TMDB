package ge.gogichaishvili.tmdb.main.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ge.gogichaishvili.tmdb.app.constants.Constants
import java.io.Serializable

@Entity(tableName = Constants.TABLE_NAME)
data class FavoriteMovieModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val overview: String,
    val image: String
) : Serializable
