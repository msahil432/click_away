package msahil432.click_away.intro;

import msahil432.click_away.database.Institute;
import msahil432.click_away.database.MyDao;

import static msahil432.click_away.list.base.BaseActivity.Types.BloodBanks;
import static msahil432.click_away.list.base.BaseActivity.Types.Chemists;
import static msahil432.click_away.list.base.BaseActivity.Types.Hospitals;

public class AddDummyData implements Runnable {

    MyDao dao;
    AddDummyData(MyDao dao){
        this.dao = dao;
    }

    @Override
    public void run() {
        addHospitals();
        addChemists();
        addBloodBanks();
    }

    private void addHospitals(){}

    private void addChemists(){}

    private void addBloodBanks(){}

}
