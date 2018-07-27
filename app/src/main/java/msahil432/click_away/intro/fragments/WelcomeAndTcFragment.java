package msahil432.click_away.intro.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import msahil432.click_away.R;

public class WelcomeAndTcFragment extends Fragment {

    public WelcomeAndTcFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome_tc, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.privacy_policy_btn)
    public void showPrivacyPolicy(){
        Uri uri = Uri.parse("https://api.msahil432.com/privacy-policy/clickaway");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }
}
