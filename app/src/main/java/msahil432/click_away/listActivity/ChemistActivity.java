package msahil432.click_away.listActivity;

import android.os.Bundle;

import msahil432.click_away.R;
import msahil432.click_away.listActivity.base.BaseActivity;

public class ChemistActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.near_drug_stores);
    }

    @Override
    protected Types setType() {
        return Types.BloodBanks;
    }

    @Override
    protected int getColorRes() {
        return R.color.drug_store;
    }
}