package msahil432.click_away.mainActivity;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

import static msahil432.click_away.extras.MyApplication.Report;

public class User implements Serializable {
    private String name, allergies, medications, bloodGroup, age;

    public User(String name, String age, String allergies, String medications, String bloodGroup) {
        this.name = name;
        this.allergies = allergies;
        this.medications = medications;
        this.bloodGroup = bloodGroup;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String blood_group) {
        this.bloodGroup = blood_group;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isEmpty(){
        if(name!=null && !name.isEmpty())
            if(age!=null && !age.isEmpty())
                if(allergies!=null && !allergies.isEmpty())
                    if(bloodGroup!=null && !bloodGroup.isEmpty())
                        return medications == null || medications.isEmpty();
        return true;
    }

    public static User Builder(Context context){
        SharedPreferences prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        String name = prefs.getString("name", "");
        String age = prefs.getString("age", "");
        String allergies = prefs.getString("allergies", "");
        String medications = prefs.getString("medications", "");
        String bg = prefs.getString("bloodGroup", "");

        return new User(name, age, allergies, medications, bg);
    }

    public boolean saveUser(Context context){
        try{
            SharedPreferences.Editor editor = context
                    .getSharedPreferences("user", Context.MODE_PRIVATE).edit();

            editor.putString("name", name);
            editor.putString("age", age);
            editor.putString("allergies", allergies);
            editor.putString("medications", medications);
            editor.putString("bloodGroup", bloodGroup);

            editor.apply();
            return true;
        }catch (Exception e){
            Report("Saving User", "Error: ", e);
        }
        return false;
    }
}
