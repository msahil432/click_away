package msahil432.click_away.extras;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by msahil432 on 14/07/18
 **/

@Module
public class InjectionProvider {

    @Provides
    @Singleton
    Retrofit getRetroFit(){
        return new Retrofit.Builder().baseUrl(RetroFitService.baseUrl).build();
    }

    @Provides
    @Singleton
    SharedPreferences getEmergencyPrefs(Context context){
        return context.getSharedPreferences("emergency", Context.MODE_PRIVATE);
    }
}
