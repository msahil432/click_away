package msahil432.click_away;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import msahil432.click_away.connections.MyGPSLocService;
import msahil432.click_away.extras.MyApplication;
import msahil432.click_away.forceClose.MyExceptionHandler;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;

import java.util.Timer;
import java.util.TimerTask;

import msahil432.click_away.intro.IntroActivity;
import msahil432.click_away.mainActivity.MainActivity;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        startService(new Intent(this, MyGPSLocService.class));
    }

    private int work = 1;
    @Override
    public void onPostResume() {
        super.onPostResume();
        if (!MyApplication.getAppPrefs(this).isSetupDone()) {
            work = 0;
        }else{
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert manager != null;
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
                return;
            }
        }
        new Timer("Work", true).schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doWork();
                    }
                });
            }
        }, 1500);
    }

    private void doWork(){
        switch (work){
            case 0: {
                startActivity(new Intent(this, IntroActivity.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, MainActivity.class));
                break;
            }
        }
        finish();
    }

    private AlertDialog alert = null;
    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        if(alert!=null)
            alert.dismiss();
        super.onDestroy();
    }
}
