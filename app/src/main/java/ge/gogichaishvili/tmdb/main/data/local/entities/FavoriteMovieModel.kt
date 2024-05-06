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
    val image: ByteArray
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FavoriteMovieModel

        if (id != other.id) return false
        if (title != other.title) return false
        if (overview != other.overview) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}
