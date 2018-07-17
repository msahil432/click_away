package msahil432.click_away.connections;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import msahil432.click_away.database.Institute;
import msahil432.click_away.database.MyDao;
import msahil432.click_away.database.MyDatabase;
import msahil432.click_away.extras.RetroFitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static msahil432.click_away.extras.MyApplication.Report;
import static msahil432.click_away.list.base.BaseActivity.Types.BloodBanks;
import static msahil432.click_away.list.base.BaseActivity.Types.Chemists;
import static msahil432.click_away.list.base.BaseActivity.Types.Hospitals;

public class FetchAndSaveWorker {

    MyGPSLocService.LocationData locationData;
    Call<JSONObject> request;

    FetchAndSaveWorker(MyGPSLocService.LocationData locationData) {
        this.locationData = locationData;
        RetroFitService service = new Retrofit.Builder()
                .baseUrl(RetroFitService.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetroFitService.class);
        request = service.getData(locationData.getLatitude()+"",
                locationData.getLongitude()+"");
    }

    private Context context;
    public void doWork(Context context){
        request.enqueue(callback);
        this.context = context;
    }

    private void saveToDb(JSONObject res){
        try {
            Report("Fetch&Save Worker", "Saving res to db");
            MyDao dao = MyDatabase.instance(context).getDao();
            if (res.has(Hospitals.dbField)) {
                JSONArray hospitals = res.getJSONArray(Hospitals.dbField);
                for(int i=0; i<hospitals.length(); i++)
                    try {
                        dao.saveAll(
                                makeAnInstitute(Hospitals.dbField, hospitals.getJSONObject(i))
                        );
                    }catch (Exception e){ e.printStackTrace();}
            }
            if (res.has(Chemists.dbField)) {
                JSONArray hospitals = res.getJSONArray(Chemists.dbField);
                for(int i=0; i<hospitals.length(); i++)
                    try {
                        dao.saveAll(
                                makeAnInstitute(Chemists.dbField, hospitals.getJSONObject(i))
                        );
                    }catch (Exception e){ e.printStackTrace();}
            }
            if (res.has(BloodBanks.dbField)) {
                JSONArray hospitals = res.getJSONArray(BloodBanks.dbField);
                for(int i=0; i<hospitals.length(); i++)
                    try {
                        dao.saveAll(
                                makeAnInstitute(BloodBanks.dbField, hospitals.getJSONObject(i))
                        );
                    }catch (Exception e){ e.printStackTrace();}
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Institute makeAnInstitute(String type, JSONObject obj) throws Exception{
        JSONArray loc = obj.getJSONArray("loc");
        Institute institute = new Institute(
                obj.getString("name"),
                obj.getString("address")+" "+obj.getString("pincode"),
                obj.getString("contact"),
                type,
                loc.getDouble(1),
                loc.getDouble(0)
        );
        return  institute;
    }

    @Override
    protected void finalize() throws Throwable {
        if(request!=null)
            request.cancel();
        super.finalize();
    }

    private Callback<JSONObject> callback = new Callback<JSONObject>(){
        @Override
        public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
            try{
                Report("",response.raw().request().url().toString());
                //Report(response.raw().request().url().toString(), response.body().toString());
                saveToDb(response.body());
            }catch (Exception e){
                Report("response", response.errorBody().toString(), e);
            }
        }

        @Override
        public void onFailure(Call<JSONObject> call, Throwable t) {
            Report("TAG", "OnFailure", t);
        }
    };
}
