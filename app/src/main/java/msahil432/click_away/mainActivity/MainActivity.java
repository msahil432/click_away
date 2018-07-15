package msahil432.click_away.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

import butterknife.ButterKnife;
import butterknife.OnClick;
import msahil432.click_away.R;
import msahil432.click_away.connections.MyGPSLocService;
import msahil432.click_away.extras.MyApplication;
import msahil432.click_away.extras.MyExceptionHandler;
import msahil432.click_away.list.BloodBankActivity;
import msahil432.click_away.list.ChemistActivity;
import msahil432.click_away.list.HospitalsActivity;

public class MainActivity extends AppCompatActivity {

    Set<String> address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private MyGPSLocService.LocationData locationData;
    @Subscribe
    public void getLocation(MyGPSLocService.LocationData locationData){
        this.locationData = locationData;
    }

    @OnClick(R.id.main_help_btn)
    public void helpMeBtn(AppCompatButton button){
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        }
        String temp = MyApplication.getContactPrefs(this).helpSound();
        int helpSound = R.raw.helpsound;
        MediaPlayer mediaPlayer;
        if(temp==null) {
            mediaPlayer = MediaPlayer.create(this, helpSound);
        }else{
            try {
                mediaPlayer = new MediaPlayer();
                temp = getExternalCacheDir().getAbsolutePath()+"/"+temp;
                mediaPlayer.setDataSource(temp);
                mediaPlayer.prepare();
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Can't Play Custom Audio, Sorry!",
                        Toast.LENGTH_LONG).show();
                mediaPlayer = MediaPlayer.create(this, helpSound);
            }
        }
        sosBtn();
        button.setClickable(false);
        HelpMeAlertDialog dialog = new HelpMeAlertDialog(this);
        dialog.show();
    }

    @OnClick(R.id.main_sos_btn)
    public void sosBtn(){
        Toast.makeText(this, "Sending SMS to Emergency Contacts",
                Toast.LENGTH_SHORT).show();
        MyApplication.ContactPrefs prefs = MyApplication.getContactPrefs(this);
        address = prefs.contacts();
        if(address!= null){
            String message = getString(R.string.sms_message)
                    +makeMapUrl();
            for(String tempAdd : address){
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(tempAdd, null, message,
                        null, null);
            }
        }
        else {
            Toast.makeText(this, "Please add some emergency contacts",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.main_drug_store_btn)
    public void chemistBtn(View v){
        startActivity(new Intent(this, ChemistActivity.class));
    }
    @OnClick(R.id.main_hospitals_btn)
    public void hospitalsBtn(View v){
        startActivity(new Intent(this, HospitalsActivity.class));
    }
    @OnClick(R.id.main_blood_btn)
    public void bloodBtn(View v){
        startActivity(new Intent(this, BloodBankActivity.class));
    }

    private String makeMapUrl(){
        String googleMapsUrl = getString(R.string.g_maps_url);
        return googleMapsUrl+"@"+locationData.getLatitude()+
                ","+locationData.getLongitude()+",15z";
    }
}
