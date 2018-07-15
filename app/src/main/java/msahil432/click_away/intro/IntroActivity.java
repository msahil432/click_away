package msahil432.click_away.intro;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

import msahil432.click_away.mainActivity.MainActivity;
import msahil432.click_away.R;
import msahil432.click_away.extras.MyApplication;

/**
 *  Created by msahil432
 */

public class IntroActivity extends AppIntro{

    WelcomeAndTcFragment fragment3;
    AddPersonalDetailsFragment fragment2;
    ContactInfoFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        fragment3 = new WelcomeAndTcFragment();
        addSlide(fragment3);
        fragment2 = new AddPersonalDetailsFragment();
        addSlide(fragment2);
        fragment = new ContactInfoFragment();
        addSlide(fragment);
        showSkipButton(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        MyApplication.getAppPrefs(this).setSetupDone(true);
        startActivity(new Intent(this, MainActivity.class));
    }

    private int i =0;
    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        i++;
        if(i>1)
            getPermissions();
        super.onSlideChanged(oldFragment, newFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1504) {
            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(this, R.string.permissions_necessary, Toast.LENGTH_SHORT).show();
                finish();
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
    }
}
