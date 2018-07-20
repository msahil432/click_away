package msahil432.click_away.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "institutes")
public class Institute {
    @PrimaryKey @NonNull
    private String uid;
    private String name, address, phone, organization;
    private double latitude, longitude;

    public Institute(@NonNull String uid, String name, String address, String phone, String organization,
                     double latitude, double longitude) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.organization = organization;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean equals(Institute obj) {
        return (obj.uid.equals(this.uid));
    }
}
