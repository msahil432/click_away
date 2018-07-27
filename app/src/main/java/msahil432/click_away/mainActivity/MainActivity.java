package msahil432.click_away.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.constraint.Group;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import msahil432.click_away.R;
import msahil432.click_away.connections.MyGPSLocService;
import msahil432.click_away.extras.MyApplication;
import msahil432.click_away.forceClose.MyExceptionHandler;
import msahil432.click_away.listActivity.BloodBankActivity;
import msahil432.click_away.listActivity.ChemistActivity;
import msahil432.click_away.listActivity.HospitalsActivity;
import msahil432.click_away.settingsActivity.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.icons_group) Group iconsGroup;
    @BindView(R.id.main_help_btn) FloatingActionButton helpBtn;

    Set<String> address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.currentThread().setUncaughtExceptionHandler(new MyExceptionHandler(this));
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextDrawable text = TextDrawable.builder()
                .beginConfig()
                    .textColor(Color.WHITE)
                    .fontSize(150)
                    .bold()
                    .toUpperCase()
                    .endConfig()
                .buildRound(getString(R.string.help), Color.RED);
        helpBtn.setImageDrawable(text);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1)
            getWindow().setNavigationBarColor(getColor(R.color.colorPrimary));
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    private MyGPSLocService.LocationData locationData;
    @Subscribe
    public void getLocation(MyGPSLocService.LocationData locationData){
        this.locationData = locationData;
    }

    MediaPlayer mediaPlayer;
    @OnClick(R.id.main_help_btn)
    public void helpMeBtn(){
        String temp = MyApplication.getContactPrefs(this).helpSound();
        int helpSound = R.raw.helpsound;
        if(temp ==null || temp.isEmpty()) {
            mediaPlayer = MediaPlayer.create(this, helpSound);
        }else{
            try {
                mediaPlayer = new MediaPlayer();
                temp = getExternalCacheDir().getAbsolutePath()+"/"+temp;
                mediaPlayer.setDataSource(temp);
                mediaPlayer.prepare();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Can't Play Custom Audio, Sorry!",
                        Toast.LENGTH_LONG).show();
                mediaPlayer = MediaPlayer.create(this, helpSound);
            }
        }
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        sosBtn();
        HelpMeAlertDialog dialog = new HelpMeAlertDialog(this);
        dialog.show();
        makeVolumeFull();
        makeScreenBrightest();
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

    @OnClick({R.id.main_drug_store_btn, R.id.drug_store_image})
    public void chemistBtn(){
        startActivity(new Intent(this, ChemistActivity.class));
    }
    @OnClick({R.id.main_hospitals_btn, R.id.hospital_image})
    public void hospitalsBtn(){
        startActivity(new Intent(this, HospitalsActivity.class));
    }
    @OnClick({R.id.main_blood_btn, R.id.blood_image})
    public void bloodBtn(){
        startActivity(new Intent(this, BloodBankActivity.class));
    }
    @OnClick(R.id.main_settings_btn)
    public void openSettings(){startActivity(new Intent(this, SettingsActivity.class));}

    private String makeMapUrl(){
        String googleMapsUrl = getString(R.string.g_maps_url);
        return googleMapsUrl+"@"+locationData.getLatitude()+
                ","+locationData.getLongitude()+",15z";
    }

    private void makeVolumeFull(){
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        }
    }

    private void makeScreenBrightest(){
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F;
        getWindow().setAttributes(layout);
    }
}

