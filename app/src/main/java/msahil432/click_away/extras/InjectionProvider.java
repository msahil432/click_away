package msahil432.click_away.extras;

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
}
