package msahil432.click_away.settingsActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;
import msahil432.click_away.R;

import static msahil432.click_away.settingsActivity.SettingEvents.resetSettings;
import static msahil432.click_away.settingsActivity.SettingEvents.sosSettings;
import static msahil432.click_away.settingsActivity.SettingEvents.userSettings;

public class SettingsListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.fragment_list_settings,
                container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.user_settings)
    public void openUserSettings(){
        EventBus.getDefault().post(userSettings);
    }

    @OnClick(R.id.emergency_settings)
    public void openSosSettings(){
        EventBus.getDefault().post(sosSettings);
    }

    @OnClick(R.id.reset_settings)
    public void resetSettings(){
        EventBus.getDefault().post(resetSettings);
    }
}
