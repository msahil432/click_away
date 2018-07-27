package msahil432.click_away.listActivity.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.db.SimpleSQLiteQuery;

import msahil432.click_away.connections.MyGPSLocService;
import msahil432.click_away.database.Institute;
import msahil432.click_away.database.MyDao;

import static msahil432.click_away.extras.MyApplication.Report;

public class BaseViewModel extends ViewModel {

    private LiveData<PagedList<Institute>> institutes;
    private PagedList.Config config = new PagedList.Config.Builder()
                                .setPageSize(20)
                                .setEnablePlaceholders(true).build();

    public BaseViewModel(final MyDao dao, final BaseActivity.Types type,
                         MyGPSLocService.LocationData locationData) {
        SimpleSQLiteQuery query = createQuery(type, locationData);
        DataSource.Factory<Integer, Institute> factory = dao.get(query);
        institutes = new LivePagedListBuilder<>(factory, config).build();
    }

    public LiveData<PagedList<Institute>> getInstitutes() {
        Report("BaseViewModel", "Sending Institutes");
        return institutes;
    }

    void setLocation(MyGPSLocService.LocationData data){

    }

    private SimpleSQLiteQuery createQuery(BaseActivity.Types type,
                                          MyGPSLocService.LocationData data){
        String query ="SELECT * FROM institutes WHERE organization = \"" +type.dbField
                +"\" ORDER BY ABS(latitude - "+data.getLatitude()+")*100000 " +
                    "+ ABS(longitude - "+data.getLongitude()+")*100000 ASC";
        return new SimpleSQLiteQuery(query);
    }
}
