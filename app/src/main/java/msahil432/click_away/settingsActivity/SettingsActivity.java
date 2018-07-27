package msahil432.click_away.settingsActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import msahil432.click_away.R;
import msahil432.click_away.extras.MyApplication;
import msahil432.click_away.intro.fragments.AddPersonalDetailsFragment;
import msahil432.click_away.intro.fragments.ContactInfoFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addListFragment();
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
        finish();
    }
}
