package msahil432.click_away

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import msahil432.click_away.extras.MyExceptionHandler
import android.location.LocationManager
import android.support.v7.app.AlertDialog
import java.util.*
import kotlin.concurrent.schedule


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(MyExceptionHandler(this))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
    }

    var work = 1
    override fun onPostResume() {
        super.onPostResume()
        if (!getSharedPreferences("basic", 0).getBoolean("SetupDone", false)) {
            work = 0
        }else{
            val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps()
                return
            }
        }
        Timer("Work", false).schedule(1000){
            doWork()
        }
    }

    private fun doWork(){
        when(work){
            0 -> {
                applicationContext.startActivity(Intent(applicationContext, IntroActivity::class.java))
            }
            1 -> {
                applicationContext.startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }
        finish()
    }

    private var alert : AlertDialog? = null
    private fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes"
                ) { _, _ -> startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))}
                .setNegativeButton("No"
                ) { dialog, _ -> finish() }
        alert = builder.create()
        alert!!.show()
    }

    override fun onDestroy() {
        if(alert!=null)
            alert!!.dismiss()
        super.onDestroy()
    }
}
