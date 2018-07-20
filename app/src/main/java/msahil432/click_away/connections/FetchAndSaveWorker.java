package msahil432.click_away.connections;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executors;

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

    private Call<MyResponseBody> request;

    FetchAndSaveWorker(MyGPSLocService.LocationData locationData) {
        RetroFitService service = new Retrofit.Builder()
                .baseUrl(RetroFitService.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetroFitService.class);
        request = service.getData(locationData.getLatitude(),
                locationData.getLongitude());
    }

    private Context context;
    void doWork(Context context){
        request.enqueue(callback);
        this.context = context;
    }

    private Callback<MyResponseBody> callback = new Callback<MyResponseBody>(){
        @Override
        public void onResponse(Call<MyResponseBody> call, final Response<MyResponseBody> response) {
            try{
                Executors.newSingleThreadExecutor().execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                saveToDb(response.body());
                            }
                        }
                );
            }catch (Exception e){
                Report("response", "error", e);
            }
        }

        @Override
        public void onFailure(Call<MyResponseBody> call, Throwable t) {
            Report("Fetch from server", "Failed", t);
        }
    };

    private void saveToDb(MyResponseBody res){
        try {
            Report("Fetch&Save Worker", "Saving res to db");
            Report("Fetch&Save Worker", res.toString());
            MyDao dao = MyDatabase.instance(context).getDao();
            List<MyResponseBody.Hospital> hospitals = res.getHospitals();
            Report("Fetch&Save Worker", "hospitals:"+hospitals.size());
            for(MyResponseBody.Hospital h : hospitals)
                try {
                    dao.save(
                            makeAnInstitute(Hospitals.dbField, h)
                    );
                }catch (Exception e){
                    Report("Saving to DB", "Hospital Singular Errors", e);
                }
            List<MyResponseBody.Chemist> chemists = res.getChemist();
            Report("Fetch&Save Worker", "chemists:"+chemists.size());
            for(MyResponseBody.Chemist h : chemists)
                try {
                    dao.save(
                            makeAnInstitute(Chemists.dbField, h)
                    );
                }catch (Exception e){
                    Report("Saving to DB", "Chemist Singular Errors", e);
                }
            List<MyResponseBody.Bloodbank> bloodBanks = res.getBloodbanks();
            Report("Fetch&Save Worker", "bloodbanks:"+bloodBanks.size());
            for(MyResponseBody.Bloodbank h : bloodBanks)
                try {
                    dao.save(
                            makeAnInstitute(BloodBanks.dbField, h)
                    );
                }catch (Exception e){
                    Report("Saving to DB", "Blood Bank Singular Errors", e);
                }
        }catch (Exception e){
            Report("Fetch&Save Worker", "saveToDb", e);
        }
    }

    private Institute makeAnInstitute(String type, MyResponseBody.Hospital h){
        return new Institute(
                h.getId(),
                h.getName(),
                h.getAddress()+" "+h.getPincode(),
                h.getContact()+"",
                type,
                h.getLoc().get(1),
                h.getLoc().get(0)
        );
    }

    private Institute makeAnInstitute(String type, MyResponseBody.Chemist h){
        return new Institute(
                h.getId(),
                h.getName(),
                h.getAddress()+" "+h.getPincode(),
                h.getContact()+"",
                type,
                h.getLoc().get(1),
                h.getLoc().get(0)
        );
    }

    private Institute makeAnInstitute(String type, MyResponseBody.Bloodbank h){
        return new Institute(
                h.getId(),
                h.getName(),
                h.getAddress()+" "+h.getPincode(),
                h.getContact()+"",
                type,
                h.getLoc().get(1),
                h.getLoc().get(0)
        );
    }

    @Override
    protected void finalize() throws Throwable {
        if(request!=null)
            request.cancel();
        super.finalize();
    }
}
