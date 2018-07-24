package msahil432.click_away.listActivity.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import msahil432.click_away.database.MyDao;

public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private MyDao dao;
    private BaseActivity.Types type;

    public MyViewModelFactory(MyDao dao, BaseActivity.Types type) {
        this.dao = dao;
        this.type = type;
    }

    @NonNull
    @Override
    public <T extends ViewModel>T create(@NonNull Class<T> modelClass) {
        return (T) new BaseViewModel(dao, type);
    }
}
