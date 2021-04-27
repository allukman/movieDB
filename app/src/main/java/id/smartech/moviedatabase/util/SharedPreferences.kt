package id.smartech.moviedatabase.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(private val context: Context) {
    companion object {
        const val PREF_NAME = "LOGIN"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

}