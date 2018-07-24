package msahil432.click_away.database;

import android.arch.paging.DataSource;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface MyDao {

    @Query("SELECT * FROM institutes where organization = :myType ORDER BY ABS(latitude - :lat)*1000 + ABS(longitude - :lng)*1000 ASC")
    public DataSource.Factory<Integer, Institute> getByType(String myType, double lat, double lng);

    @RawQuery(observedEntities = Institute.class)
    public DataSource.Factory<Integer, Institute> get(SupportSQLiteQuery query);

    @Insert(onConflict = IGNORE)
    public void save(Institute... institutes);
}