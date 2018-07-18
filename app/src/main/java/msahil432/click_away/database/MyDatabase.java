package msahil432.click_away.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Institute.class}, version = 3, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase instance;

    public abstract MyDao getDao();

    public static MyDatabase instance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context, MyDatabase.class, "institutes-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
