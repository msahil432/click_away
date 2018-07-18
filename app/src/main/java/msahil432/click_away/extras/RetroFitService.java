package msahil432.click_away.extras;

import org.json.JSONObject;

import msahil432.click_away.connections.MyResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by msahil432 on ${Date}
 **/
public interface RetroFitService {

    String baseUrl = "http://desolate-bastion-73012.herokuapp.com";

    @Headers({"User-Agent: Click-Away"})
    @FormUrlEncoded
    @POST("/setup/")
    Call<MyResponseBody> getData(@Field("lat") double latitude, @Field("long") double longitude);

    @POST("https://msahil432api.herokuapp.com/android/errors")
    Call<JSONObject> reportError(@Body JSONObject object);
}
