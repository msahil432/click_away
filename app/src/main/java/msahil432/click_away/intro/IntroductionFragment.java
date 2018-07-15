package msahil432.click_away.intro;

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

public class IntroductionFragment extends Fragment {

    public IntroductionFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_introduction, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.privacy_policy_btn)
    public void showPrivacyPolicy(){
        Uri uri = Uri.parse(getString(R.string.privacy_policy));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }
}
