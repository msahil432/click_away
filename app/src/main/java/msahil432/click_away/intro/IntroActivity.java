package msahil432.click_away.intro;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import org.json.JSONObject;

import msahil432.click_away.Database;
import msahil432.click_away.MainActivity;
import msahil432.click_away.connections.myGPSProvider;
import msahil432.click_away.connections.myHTTP;

/**
 *  Created by msahil432
 */

public class IntroActivity extends AppIntro{

    int i =0;

    IntroductionFragment fragment3;
    PersonalDetailsFragment fragment2;
    IntroFragmentDetails fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        fragment3 = new IntroductionFragment();
        addSlide(fragment3);
        fragment2 = new PersonalDetailsFragment();
        addSlide(fragment2);
        fragment = new IntroFragmentDetails();
        addSlide(fragment);
        showSkipButton(false);

        myGPSProvider gps = new myGPSProvider(this);
        gps.refresh(this);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        SharedPreferences prefs = getSharedPreferences("basic",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("SetupDone", true);
        editor.apply();
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        i++;
        if(i>1)
            getPermissions();
        if(i==4){
            async a = new async();
            a.execute(getApplicationContext());
        }

        super.onSlideChanged(oldFragment, newFragment);
    }

    Database db;
    myHTTP my = myHTTP.instance();
    class async extends AsyncTask<Context, Void, String>{
        @Override
        protected String doInBackground(Context... params) {
            try {
                String url = "http://desolate-bastion-73012.herokuapp.com/setup";
                JSONObject object = new JSONObject();
                myGPSProvider gps = new myGPSProvider(params[0]);
                double t1=gps.getLat(), t2= gps.getLon();
                if(t1==-1|t2==-1){
                    object.accumulate("lat", 28.72);
                    object.accumulate("long", 77.120);
                }else {
                    object.accumulate("lat", t1);
                    object.accumulate("long", t2);
                }
                return my.postJson(url, object);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v("obj", s);
            db = new Database(getApplicationContext());
            async2 a2 = new async2();
            a2.execute(s);
        }
    }

    class async2 extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            db.setup(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(IntroActivity.this, "The db has been created successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreferences prefs = getSharedPreferences("basic",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (requestCode == 1504) {
            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(this, "These permissions were necessary for app to work. Sorry!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                editor.putBoolean("PermissionsDone", true);
                editor.apply();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getPermissions() {
        String[] requiredPerms = new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission_group.PHONE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        Context context = getApplicationContext();
        int pD = PackageManager.PERMISSION_DENIED;
        int pC = 0;
        //Checking permissions
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == pD) {
            pC += 1;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == pD) {
            pC += 1;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == pD) {
            pC += 1;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == pD) {
            pC += 1;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == pD) {
            pC += 1;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == pD) {
            pC += 1;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == pD) {
            pC += 1;
        }

        if (pC != 0)
            ActivityCompat.requestPermissions(this, requiredPerms, 1504);
        else {
            SharedPreferences prefs = getSharedPreferences("basic",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("PermissionsDone", true);
            editor.apply();
        }
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Intro Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}
