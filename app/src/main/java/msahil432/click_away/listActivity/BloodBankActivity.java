package msahil432.click_away.listActivity;

import android.os.Bundle;

import msahil432.click_away.R;
import msahil432.click_away.listActivity.base.BaseActivity;

public class BloodBankActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.near_blood_banks);
    }

    @Override
    protected Types setType() {
        return Types.BloodBanks;
    }

    @Override
    protected int getColorRes() {
        return R.color.blood_bank_color;
    }
}