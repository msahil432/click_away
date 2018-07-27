package msahil432.click_away.listActivity.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import msahil432.click_away.connections.MyGPSLocService;
import msahil432.click_away.database.MyDao;

public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private MyDao dao;
    private BaseActivity.Types type;
    private MyGPSLocService.LocationData locData;

    public MyViewModelFactory(MyDao dao, BaseActivity.Types type,
                              MyGPSLocService.LocationData locationData) {
        this.dao = dao;
        this.type = type;
        locData = locationData;
    }

    @NonNull
    @Override
    public <T extends ViewModel>T create(@NonNull Class<T> modelClass) {
        return (T) new BaseViewModel(dao, type, locData);
    }
}
