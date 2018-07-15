package msahil432.click_away.extras;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by msahil432 on ${Date}
 **/
public interface RetroFitService {

    String baseUrl = "http://desolate-bastion-73012.herokuapp.com";

    @Headers({"User-Agent: Click-Away"})
    @POST("/setup?lat={lat}&long={lng}")
    Call<JSONObject> getData(@Path("lat") String latitude, @Path("long") String longitude);

    @POST("https://msahil432api.herokuapp.com/android/errors")
    Call<JSONObject> reportError(@Body JSONObject object);
}
