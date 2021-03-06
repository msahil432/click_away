package msahil432.click_away.forceClose;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;
import msahil432.click_away.R;
import msahil432.click_away.SplashActivity;
import msahil432.click_away.extras.RetroFitService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static msahil432.click_away.extras.MyApplication.DEBUG_ON;
import static msahil432.click_away.extras.MyApplication.Report;

public class ForceCloseActivity extends AppCompatActivity {
    JSONObject obj;
    Bundle data;
    String COE, DB, DN, DM, DI, P, S, SR, SI, TIME, AI, ACT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_close);

        ButterKnife.bind(this);

        Toast.makeText(getApplicationContext(), "Oh Ho, You have found a bug. :)", Toast.LENGTH_LONG).show();

        obj = new JSONObject();
        data = getIntent().getExtras();
        assert data !=null;
    }

    protected void onResume(){
        super.onResume();
        buildData();
        showData();
    }

    private void buildData(){
        try {
            Exception ex = (Exception) data.getSerializable("ex");
            Report("Force Close ","Exception: ", ex);
        }catch (Exception e){
            Report("FC ACT", "Couldn't cast to exception", e);
        }

        COE = data.getString("CAUSE_OF_ERROR");
        ACT = data.getString("Activity");
        DB = Build.BRAND;
        DN = Build.DEVICE;
        DM = Build.MODEL;
        DI = Build.ID;
        P = Build.PRODUCT;
        S = Build.VERSION.CODENAME+" "+Build.VERSION.SDK_INT;
        SR = Build.VERSION.RELEASE;
        SI = Build.VERSION.INCREMENTAL;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            AI = (pInfo.versionName+" "+ pInfo.versionCode);
        }catch (Exception e){
            AI = "ERROR "+e.getMessage();
        }

        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TIME = df.format(c.getTime());
    }

    private void showData(){
        String LINE_SEPARATOR = "\n";

        String errorReport2 ="";
        if(DEBUG_ON){
            errorReport2 = "- CAUSE OF ERROR ************\n\n" + COE ;
        }

        errorReport2+="\n-DEVICE INFORMATION ***********\n" +
                "Brand: " + DB +
                LINE_SEPARATOR +
                "Device: " + DB +
                LINE_SEPARATOR +
                "Model: " + DM +
                LINE_SEPARATOR +
                "Id: " + DI +
                LINE_SEPARATOR +
                "Product: " + P +
                LINE_SEPARATOR +
                "\n- FIRMWARE ************\n" +
                "APP: " +
                ACT + " " + AI +
                LINE_SEPARATOR +
                "SDK: " + S +
                LINE_SEPARATOR +
                "Release: " + SR +
                LINE_SEPARATOR +
                "Incremental: " + SI +
                LINE_SEPARATOR;
        ((TextView)findViewById(R.id.fc_ai)).setText(errorReport2);
    }

    @OnClick(R.id.send_btn)
    public void send(){
        Context context = getApplicationContext();
        Toast.makeText(context, R.string.report_thanks, Toast.LENGTH_LONG).show();
        try {
            obj.accumulate("Activity", ACT);
            obj.accumulate("TIME", TIME);
            obj.accumulate("CAUSE_OF_ERROR",COE);
            obj.accumulate("DEVICE_BRAND",DB);
            obj.accumulate("DEVICE_NAME",DN);
            obj.accumulate("DEVICE_MODEL",DM);
            obj.accumulate("DEVICE_ID",DI);
            obj.accumulate("PRODUCT",P);
            obj.accumulate("SDK",S);
            obj.accumulate("SDK_RELEASE",SR);
            obj.accumulate("SDK_INCREMENTAL",SI);
            obj.accumulate("APP_INFO", AI);

            JSONObject object = new JSONObject();
            object.put("app", "Click-Away");
            object.put("log", obj);

            RetroFitService service = new Retrofit.Builder()
                    .baseUrl(RetroFitService.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RetroFitService.class);

            service.reportError(object).execute();

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    cancelBtn();
                }
            }, 5000);
        }catch (Exception error){ }
    }

    @OnClick(R.id.cancel_btn)
    public void cancelBtn(){
        Toast.makeText(this, R.string.future_error_prevention, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
