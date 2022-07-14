package com.example.filmnettest

import android.app.Activity
import android.app.Application
import android.content.Context
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import java.util.*
import kotlin.collections.ArrayList

class App : LocalizationApplication() {



    companion object {
        const val LANGUAGE_FARSI = "fa"
        const val LANGUAGE_IRAN_COUNTRY = "IR"
    }

    override fun getDefaultLanguage(base: Context): Locale {
        //return Locale.getDefault()
        return Locale.Builder().setLanguage(LANGUAGE_FARSI).setRegion(LANGUAGE_IRAN_COUNTRY).build()
    }



}