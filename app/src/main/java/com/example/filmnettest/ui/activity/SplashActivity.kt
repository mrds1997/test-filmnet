package com.example.filmnettest.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.example.filmnettest.BuildConfig
import com.example.filmnettest.MainActivity
import com.example.filmnettest.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : LocalizationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        tvVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)

        Handler().postDelayed({
            showMainActivity()
        }, 3000)
    }

    private fun showMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}