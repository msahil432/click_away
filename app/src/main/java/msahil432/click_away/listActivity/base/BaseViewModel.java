package msahil432.click_away.listActivity.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.db.SimpleSQLiteQuery;

import msahil432.click_away.database.Institute;
import msahil432.click_away.database.MyDao;

import static msahil432.click_away.extras.MyApplication.Report;

public class BaseViewModel extends ViewModel {
    private LiveData<PagedList<Institute>> institutes;

    public BaseViewModel(final MyDao dao, final BaseActivity.Types type) {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "SELECT * FROM institutes where organization = "+type.dbField
                     +" ORDER BY ABS(latitude - 28.7501267)*1000 + ABS(longitude - 77.117585)*1000 ASC"
        );
        DataSource.Factory<Integer, Institute> factory = dao.get(query);
        institutes = new LivePagedListBuilder<>(factory,
                new PagedList.Config.Builder()
                        .setPageSize(20)
                        .setEnablePlaceholders(true).build()
        ).build();
    }

    public LiveData<PagedList<Institute>> getInstitutes() {
        Report("BaseViewModel", "Sending Institutes");
        return institutes;
    }
}
