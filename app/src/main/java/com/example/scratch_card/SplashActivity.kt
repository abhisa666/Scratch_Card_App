package com.example.scratch_card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler


class SplashActivity : AppCompatActivity() {
    /**
     * Duration of wait
     */
    private val SPLASH_DISPLAY_LENGTH = 1000

    /**
     * Called when the activity is first created.
     */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        val secondsDelayed = 1
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, (2 * 1000).toLong())
    }
}