package msahil432.click_away.mainActivity;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
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

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public static User Builder(Context context){
        SharedPreferences prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        String name = prefs.getString("name", null);
        if(name==null) return null;

        String age = prefs.getString("age", null);
        if(age==null) return null;

        String allergies = prefs.getString("allergies", null);
        if(allergies==null) return null;

        String medications = prefs.getString("medications", null);
        if(medications==null) return null;

        String bg = prefs.getString("bloodGroup", null);
        if(bg==null) return null;

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
            e.printStackTrace();
        }
        return false;
    }
}
