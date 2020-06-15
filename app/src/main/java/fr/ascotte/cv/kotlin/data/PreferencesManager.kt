package fr.ascotte.cv.kotlin.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class PreferencesManager(private val activity: Activity) {

    private val key = "fr.ascotte.cv.kotlin"
    private val keyMode = "settings.mode"
    private var sharedPreferences: SharedPreferences

    init {

        sharedPreferences = activity.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    fun isDarkThemeOn() : Boolean{

        return sharedPreferences.getBoolean(keyMode, true)
    }

    fun setDarkTheme(on:Boolean){

        val prefEditor = activity.getSharedPreferences(key, Context.MODE_PRIVATE).edit()
        prefEditor.putBoolean(keyMode, on)
        prefEditor.apply()
    }
}