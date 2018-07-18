package msahil432.click_away.extras;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import msahil432.click_away.connections.MyGPSLocService;

public class MyApplication extends Application {

    public static boolean DEBUG_ON = false;
    public static void Report(String location, String description, Throwable... t){
        if(!DEBUG_ON)   return;
        Log.e("TAG", location+": "+description);
        for(Throwable th: t)
            Log.e("TAG", location+": "+description, th);
    }

    public static void Report(Context context, String location, String description,
                           Throwable... t){
        if(!DEBUG_ON)   return;
        Report(location, description, t);
        Toast.makeText(context, location+": "+description, Toast.LENGTH_SHORT)
                .show();
        for (Throwable th: t) {
            Toast.makeText(context, location+": Error-"+th.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private static AppPrefs appPrefs;
    private static ContactPrefs contactPrefs;
    private static LastLocation locPrefs;

    public static AppPrefs getAppPrefs(Context context) {
        if(appPrefs == null)
            appPrefs = new AppPrefs(context);
        return appPrefs;
    }

    public static class AppPrefs{
        private SharedPreferences prefs;
        private AppPrefs(Context context){
            prefs = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        }

        public boolean isSetupDone(){
            return prefs.getBoolean("setup", false);
        }

        public void setSetupDone(boolean setupDone){
            prefs.edit().putBoolean("setup", setupDone).apply();
        }
    }




    public static ContactPrefs getContactPrefs(Context context) {
        if(contactPrefs==null)
            contactPrefs = new ContactPrefs(context);
        return contactPrefs;
    }

    public static class ContactPrefs{
        SharedPreferences prefs;

        private ContactPrefs(Context context){
            prefs = context.getSharedPreferences("emergency", Context.MODE_PRIVATE);
        }

        public String helpSound(){
            return prefs.getString("helpSound", null);
        }

        public void setHelpSound(String name){
            prefs.edit().putString("helpSound", name).apply();
        }

        public Set<String> contacts(){
            return prefs.getStringSet("contacts", null);
        }

        public boolean addContact(String phone){
            Set<String> contacts = prefs.getStringSet("contacts", null);
            if(contacts==null) {
                contacts = new HashSet<>();
                contacts.add(phone);
                return true;
            }
            for(String t : contacts)
                if(t.equals(phone))
                    return false;
            contacts.add(phone);
            return true;
        }
    }

    public static LastLocation getLastLocation(Context context){
        if(locPrefs==null)
            locPrefs = new LastLocation(context);
        return locPrefs;
    }

    public static class LastLocation{
        private SharedPreferences prefs;
        private LastLocation(Context context){
            prefs = context.getSharedPreferences("last_loc", MODE_PRIVATE);
        }

        public void setLastLocation(MyGPSLocService.LocationData location){
            prefs.edit().putString("latitude", location.getLatitude()+"").apply();
            prefs.edit().putString("longitude", location.getLongitude()+"").apply();
            prefs.edit().putString("altitude", location.getAltitude()+"").apply();
        }

        public MyGPSLocService.LocationData getLastLocatino(){
            return new MyGPSLocService.LocationData(
                    Double.valueOf(prefs.getString("latitude", "28.72")),
                    Double.valueOf(prefs.getString("longitude", "77.120")),
                    Double.valueOf(prefs.getString("altitude", "12000"))
            );
        }
    }
}
