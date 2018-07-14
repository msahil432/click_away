package msahil432.click_away.extras

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log

import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by sahil on 7/4/17.
 *
 *
 */

class MyExceptionHandler(private val myContext: Activity) : java.lang.Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        val stackTrace = StringWriter()
        exception.printStackTrace(PrintWriter(stackTrace))
        val errorReport = StringBuilder()
        errorReport.append(stackTrace.toString())
        Log.d("EXP", errorReport.toString())

        val b = Bundle()
        b.putString("CAUSE_OF_ERROR", exception.message + stackTrace.toString())

        val intent = Intent(myContext, ForceCloseActivity::class.java)
        intent.putExtras(b)
        myContext.startActivity(intent)
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(10)
    }
}