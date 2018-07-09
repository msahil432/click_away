package msahil432.click_away

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import msahil432.click_away.Extras.mExceptionHandler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(mExceptionHandler(this))
        setContentView(R.layout.activity_splash)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onPostResume() {
        super.onPostResume()
        val myPrefs = getSharedPreferences("basic", 0)
        if (!myPrefs.getBoolean("SetupDone", false))
            startActivity(Intent(applicationContext, IntroActivity::class.java))
        else
            startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}
