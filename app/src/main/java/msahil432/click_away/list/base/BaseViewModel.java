package msahil432.click_away.list.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import msahil432.click_away.database.Institute;
import msahil432.click_away.database.MyDao;

public class BaseViewModel extends ViewModel {
    private LiveData<PagedList<Institute>> institutes;

    public BaseViewModel(MyDao dao, BaseActivity.Types type) {
        DataSource.Factory<Integer, Institute> factory = dao.getAllByType(type.dbField);
        institutes = new LivePagedListBuilder<>(factory, 30).build();
    }

    public LiveData<PagedList<Institute>> getInstitutes() {
        return institutes;
    }
}
