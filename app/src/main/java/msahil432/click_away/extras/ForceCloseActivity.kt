package msahil432.click_away.extras


import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

import org.json.JSONObject

import java.text.SimpleDateFormat
import java.util.Calendar

import msahil432.click_away.MainActivity
import msahil432.click_away.R
import msahil432.click_away.connections.myHTTP

/**
 * Created by sahil on 7/4/17.
 *
 */

class ForceCloseActivity : AppCompatActivity() {

    lateinit var obj: JSONObject
    lateinit var COE: String
    lateinit var DB: String
    lateinit var DN: String
    lateinit var DM: String
    lateinit var DI: String
    lateinit var P: String
    lateinit var S: String
    lateinit var SR: String
    lateinit var SI: String
    lateinit var TIME: String
    lateinit var AI: String
    private val LINE_SEPARATOR = "\n"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fc_activity)

        Toast.makeText(this, "Oh Ho, You have found a bug. :)", Toast.LENGTH_LONG).show()

        obj = JSONObject()
        val data = intent.extras!!
        COE = data.getString("CAUSE_OF_ERROR")
        DB = Build.BRAND
        DN = Build.DEVICE
        DM = Build.MODEL
        DI = Build.ID
        P = Build.PRODUCT
        S = Build.VERSION.CODENAME + " " + Build.VERSION.SDK_INT
        SR = Build.VERSION.RELEASE
        SI = Build.VERSION.INCREMENTAL
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            AI = pInfo.versionName + " " + pInfo.versionCode
        } catch (e: Exception) {
            AI = "ERROR " + e.message
        }

        val c = Calendar.getInstance()
        @SuppressLint("SimpleDateFormat")
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        TIME = df.format(c.time)

        try {
            obj.accumulate("TIME", TIME)
            obj.accumulate("CAUSE_OF_ERROR", COE)
            obj.accumulate("DEVICE_BRAND", DB)
            obj.accumulate("DEVICE_NAME", DN)
            obj.accumulate("DEVICE_MODEL", DM)
            obj.accumulate("DEVICE_ID", DI)
            obj.accumulate("PRODUCT", P)
            obj.accumulate("SDK", S)
            obj.accumulate("SDK_RELEASE", SR)
            obj.accumulate("SDK_INCREMENTAL", SI)
            obj.accumulate("APP_INFO", AI)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val errorReport2 = StringBuilder()
        errorReport2.append("************ CAUSE OF ERROR ************\n\n")
        errorReport2.append(COE)

        errorReport2.append("\n************ DEVICE INFORMATION ***********\n")
        errorReport2.append("Brand: ")
        errorReport2.append(DB)
        errorReport2.append(LINE_SEPARATOR)
        errorReport2.append("Device: ")
        errorReport2.append(DB)
        errorReport2.append(LINE_SEPARATOR)
        errorReport2.append("Model: ")
        errorReport2.append(DM)
        errorReport2.append(LINE_SEPARATOR)
        errorReport2.append("Id: ")
        errorReport2.append(DI)
        errorReport2.append(LINE_SEPARATOR)
        errorReport2.append("Product: ")
        errorReport2.append(P)
        errorReport2.append(LINE_SEPARATOR)
        errorReport2.append("\n************ FIRMWARE ************\n")
        errorReport2.append("SDK: ")
        errorReport2.append(S)
        errorReport2.append(LINE_SEPARATOR)
        errorReport2.append("Release: ")
        errorReport2.append(SR)
        errorReport2.append(LINE_SEPARATOR)
        errorReport2.append("Incremental: ")
        errorReport2.append(SI)
        errorReport2.append(LINE_SEPARATOR)

        (findViewById<View>(R.id.fc_ai) as TextView).text = errorReport2.toString()

    }

    fun sendButton(v: View) {
        Toast.makeText(this, "Thanks for helping us", Toast.LENGTH_LONG).show()
        sendReport().execute(obj)
    }

    fun cancelButton(v: View) {
        Toast.makeText(this, "You could have helped us :(", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MainActivity::class.java))
        Toast.makeText(this, "Restarting Application", Toast.LENGTH_SHORT).show()
    }

    private inner class sendReport : AsyncTask<JSONObject, Void, String>() {
        override fun doInBackground(vararg params: JSONObject): String? {
            try {
                Log.d("FC Data", params[0].toString())
                myHTTP.instance().postJson("/droiderrors", params[0])
            } catch (e: Exception) {
                e.printStackTrace()
                return e.message
            }

            return null
        }

        override fun onPostExecute(aVoid: String?) {
            super.onPostExecute(aVoid)
            if (aVoid == null)
                Toast.makeText(applicationContext, "Report is sent", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(applicationContext, "It looks like it's bug's season.\nError: $aVoid", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

}