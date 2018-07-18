package msahil432.click_away.database;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MyDao {

    @Query("SELECT * FROM institutes WHERE type LIKE :mtype")
    public DataSource.Factory<Integer, Institute> getByType(String mtype);

    @Query("SELECT * FROM institutes")
    public List<Institute> get(String mtype);

    @Insert(onConflict = REPLACE)
    public void save(Institute... institutes);
}