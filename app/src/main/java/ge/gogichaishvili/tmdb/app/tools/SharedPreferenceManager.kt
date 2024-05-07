package ge.gogichaishvili.tmdb.app.tools

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {

    private val preference: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preference.edit()

    fun saveSelectedLanguageCode(languageKey: String) {
        editor.apply {
            putString(SELECTED_LANGUAGE_KEY, languageKey)
        }.apply()
    }

    fun getSelectedLanguageCode(): String {
        return preference.getString(SELECTED_LANGUAGE_KEY, "en") ?: "en"
    }

    companion object {
        private const val PREFERENCE_NAME = "MySharedPreference"
        private const val SELECTED_LANGUAGE_KEY = "selected_language_key"
    }

}