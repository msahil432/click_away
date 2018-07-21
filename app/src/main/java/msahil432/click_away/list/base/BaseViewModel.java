package msahil432.click_away.list.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.List;
import java.util.concurrent.Executors;

import msahil432.click_away.database.Institute;
import msahil432.click_away.database.MyDao;

import static msahil432.click_away.extras.MyApplication.Report;

public class BaseViewModel extends ViewModel {
    private LiveData<PagedList<Institute>> institutes;

    public BaseViewModel(final MyDao dao, final BaseActivity.Types type) {
        DataSource.Factory<Integer, Institute> factory = dao.getByType(type.dbField);
        institutes = new LivePagedListBuilder<>(factory,
                                                new PagedList.Config.Builder()
                                                .setPageSize(30)
                                                .setEnablePlaceholders(true).build()
                            ).build();
        Executors.newSingleThreadExecutor().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        List<Institute> m = dao.get();
                        Report("Size ",m.size()+"");
                        for (Institute i: m) {
                            Report("Size ",i.getOrganization());
                        }
                    }
                }
        );
    }

    public LiveData<PagedList<Institute>> getInstitutes() {
        Report("BaseViewModel", "Sending Institutes");
        return institutes;
    }
}
