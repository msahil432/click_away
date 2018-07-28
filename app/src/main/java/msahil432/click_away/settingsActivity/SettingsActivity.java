package msahil432.click_away.settingsActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import msahil432.click_away.R;
import msahil432.click_away.SplashActivity;
import msahil432.click_away.extras.MyApplication;
import msahil432.click_away.forceClose.MyExceptionHandler;
import msahil432.click_away.intro.fragments.AddPersonalDetailsFragment;
import msahil432.click_away.intro.fragments.ContactInfoFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Thread.currentThread().setUncaughtExceptionHandler(new MyExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EventBus.getDefault().register(this);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(R.string.settings);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        addListFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventListener(SettingEvents event){
        switch (event){
            case userSettings:
                addUserFragment();
                break;
            case sosSettings:
                addSosFragment();
                break;
            case resetSettings:
                resetAll();
                break;
        }
    }

    private void addListFragment(){
        SettingsListFragment fragment = new SettingsListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_frame, fragment, "LIST");
        transaction.commit();
    }

    private void addUserFragment(){
        AddPersonalDetailsFragment fragment = new AddPersonalDetailsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_frame, fragment, "User");
        transaction.commit();
        Toast.makeText(this, "Tap on field to update", Toast.LENGTH_LONG).show();
    }

    private void addSosFragment(){
        ContactInfoFragment fragment = new ContactInfoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container_frame, fragment, "Sos");
        transaction.commit();
    }

    private void resetAll(){
        MyApplication.getAppPrefs(this).setSetupDone(false);
        MyApplication.getContactPrefs(this).clear();
        AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                PendingIntent.getActivity(getApplicationContext(), 0,
                        new Intent(this, SplashActivity.class), 0));
        finishAffinity();
    }
}
