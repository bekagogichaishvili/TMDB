package ge.gogichaishvili.tmdb.main.presentation.activities.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ge.gogichaishvili.tmdb.app.tools.ConnectivityLiveData
import ge.gogichaishvili.tmdb.app.tools.SingleLiveEvent

open class BaseActivity() : AppCompatActivity() {

    open lateinit var connectivityLiveData: ConnectivityLiveData
    private val _isConnectionLiveData = SingleLiveEvent<Boolean>()
    open val isConnectionLiveData get() = _isConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityLiveData = ConnectivityLiveData(this.application)
        connectivityLiveData.observe(this, Observer { isAvailable ->
            when (isAvailable) {
                true -> {
                    _isConnectionLiveData.postValue(true)
                }

                false -> {
                    _isConnectionLiveData.postValue(false)
                }
            }
        })

    }

}
