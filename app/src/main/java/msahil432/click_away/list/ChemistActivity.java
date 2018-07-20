package msahil432.click_away.list;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import msahil432.click_away.R;
import msahil432.click_away.list.base.BaseActivity;

public class ChemistActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            setTitle(R.string.near_drug_stores);
            ColorDrawable color;
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
                color = new ColorDrawable(getColor(R.color.drug_store));
                getWindow().setStatusBarColor(getColor(R.color.drug_store));
            }else{
                color = new ColorDrawable(getResources().getColor(R.color.drug_store));
            }
            getSupportActionBar().setBackgroundDrawable(color);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    protected Types setType() {
        return Types.BloodBanks;
    }
}