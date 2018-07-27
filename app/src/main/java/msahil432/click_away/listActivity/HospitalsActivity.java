package msahil432.click_away.listActivity;

import android.os.Bundle;

import msahil432.click_away.R;
import msahil432.click_away.listActivity.base.BaseActivity;

public class HospitalsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.near_hospitals);
    }

    @Override
    protected Types setType() {
        return Types.Hospitals;
    }

    @Override
    protected int getColorRes() {
        return R.color.hospital_color;
    }
}