package msahil432.click_away.intro.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import msahil432.click_away.R;
import msahil432.click_away.extras.MyApplication;

import static android.app.Activity.RESULT_OK;
import static msahil432.click_away.extras.MyApplication.Report;

public class ContactInfoFragment extends Fragment {

    public final static int Contact_ResultCode=1011;

    MyApplication.ContactPrefs prefs;

    @BindView(R.id.contact1) AppCompatButton contact1;
    @BindView(R.id.contact2) AppCompatButton contact2;
    @BindView(R.id.contact3) AppCompatButton contact3;
    @BindView(R.id.contact4) AppCompatButton contact4;

    public ContactInfoFragment() {    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_details, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        prefs = MyApplication.getContactPrefs(getContext());
        Set<String> contacts = prefs.contacts();
        if(contacts!=null){
            String arr[] = (String[]) contacts.toArray();
            Context context = getContext();
            String t = getName(context, arr[0]);
            if(t!=null && !t.isEmpty())
                contact1.setText(t);
            t = getName(context, arr[1]);
            if(t!=null && !t.isEmpty())
                contact2.setText(t);
            t = getName(context, arr[2]);
            if(t!=null && !t.isEmpty())
                contact3.setText(t);
            t = getName(context, arr[3]);
            if(t!=null && !t.isEmpty())
                contact4.setText(t);
        }
    }

    MediaPlayer mediaPlayer;
    @OnClick(R.id.play_help_intro)
    public void playSound(){
        Context context = getContext();
        if(context==null) {
            Report("TAG", "Null Context");
            return;
        }
        String temp = prefs.helpSound();
        if(temp==null) {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.helpsound);
            mediaPlayer.start();
        }else{
            try {
                mediaPlayer = new MediaPlayer();
                temp = context.getExternalCacheDir().getAbsolutePath() + "/" + temp;
                mediaPlayer.setDataSource(temp);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }catch (Exception e){
                e.printStackTrace();
                Report("help", temp, e);
                Toast.makeText(context, "Error while Playing", Toast.LENGTH_LONG).show();
                mediaPlayer.stop();
                return;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "Stop", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mediaPlayer!= null)
                    mediaPlayer.stop();
                dialog.dismiss();
            }
        });
        dialog.setMessage("Playing... \n");
        dialog.show();
    }

    MediaRecorder recorder;
    @OnClick(R.id.record_help_intro)
    public void recordNew(){
        Context context = getContext();
        if(context==null) {
            Report("TAG", "Null Context");
            return;
        }
        try {
            String fileName = context.getExternalCacheDir().getAbsolutePath();
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            String t = String.valueOf(System.currentTimeMillis());
            recorder.setOutputFile(fileName + "/" + t);
            prefs.setHelpSound(t);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            try {
                recorder.prepare();
                recorder.start();
                Report("Recording Audio: ", fileName);
            } catch (Exception e) {
                Report("Recording Audio: ", fileName, e);
                prefs.setHelpSound("");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            AlertDialog dialog = builder.create();
            dialog.setButton(Dialog.BUTTON_NEGATIVE, "Stop", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    recorder.release();
                    recorder = null;
                    dialog.dismiss();
                }
            });
            dialog.setMessage("Press Stop when done \n");
            dialog.show();
        }catch (Exception e){
            Report("Recording Audio: ", "error", e);
            prefs.setHelpSound("");
        }
    }

    AppCompatButton currentBtn;
    @OnClick({R.id.contact1, R.id.contact2, R.id.contact3, R.id.contact4})
    public void chooseContacts(AppCompatButton btn){
        Intent in = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        currentBtn = btn;
        startActivityForResult(in, Contact_ResultCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Context context = getContext();
        if(resultCode==RESULT_OK && requestCode == Contact_ResultCode){
            Uri uri = data.getData();
            Cursor cursor = context.getContentResolver().query(uri,
                    null, null, null, null);
            if(cursor==null || !cursor.moveToFirst()) return;
            String phoneNo = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER));
            if(prefs.addContact(phoneNo))
                currentBtn.setText(getName(context, phoneNo));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getName(Context context, String address) {
        if (address == null || address.isEmpty())
            return address;
        while(address.contains(" ")){
            address = address.replace(" ", "");
        }

        Cursor cursor;
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(address));
        String name = address;
        try {
            cursor = context.getContentResolver().query(uri,
                    new String[]{
                            BaseColumns._ID,
                            ContactsContract.PhoneLookup.DISPLAY_NAME
                    }, null, null, null);
            if (cursor != null && cursor.moveToFirst())
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            cursor.close();
        } catch (Exception e) {
            Report("", "Failed to find name for address " + address, e);
        }
        return name;
    }
}
