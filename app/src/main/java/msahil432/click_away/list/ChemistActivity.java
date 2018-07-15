package msahil432.click_away.list;

import android.arch.lifecycle.ViewModelProviders;

import msahil432.click_away.list.base.BaseActivity;
import msahil432.click_away.list.base.BaseViewModel;

public class ChemistActivity extends BaseActivity {
    @Override
    protected BaseViewModel setViewModel() {
        return ViewModelProviders.of(this).get(BaseViewModel.class);
    }

    @Override
    protected Types setType() {
        return Types.BloodBanks;
    }
}