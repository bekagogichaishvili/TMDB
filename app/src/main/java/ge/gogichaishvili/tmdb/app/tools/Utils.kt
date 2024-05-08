package ge.gogichaishvili.tmdb.app.tools

import android.content.Context
import android.media.MediaPlayer
import ge.gogichaishvili.tmdb.R

object Utils {
    fun playAlertSound(context: Context) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.alert)
        mediaPlayer?.start()
    }
}



