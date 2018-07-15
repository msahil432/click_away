package msahil432.click_away.extras;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class MyApplication extends Application {

    private static AppPrefs appPrefs;
    private static ContactPrefs contactPrefs;

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

        public void setHelpSoung(String name){
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
}
