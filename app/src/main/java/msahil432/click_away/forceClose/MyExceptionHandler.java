package msahil432.click_away.forceClose;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MyExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {

    private final Activity activity;

    public MyExceptionHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));

        Bundle b = new Bundle();
        b.putString("CAUSE_OF_ERROR", exception.getMessage()+":\n "+stackTrace.toString());
        b.putString("Activity", activity.getClass().getSimpleName());
        b.putSerializable("ex", exception);
        Intent intent = new Intent(activity, ForceCloseActivity.class);
        intent.putExtras(b);
        activity.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}