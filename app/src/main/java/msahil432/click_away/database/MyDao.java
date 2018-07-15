package msahil432.click_away.database;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MyDao {

    @Query("Select * from institutes where type == :type")
    public DataSource.Factory<Integer, Institute> getAllByType(String type);

    @Insert(onConflict = REPLACE)
    public void saveAll(Institute... institutes);

    @Delete
    public void deleteAll();
}