package msahil432.click_away.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "institutes")
public class Institute {
    @PrimaryKey(autoGenerate = true)
    private long uid;

    private String name, address, phone, type;
    private double latitude, longitude;

    public Institute(long uid, String name, String address, String phone, String type,
                     double latitude, double longitude) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Ignore
    public Institute(String name, String address, String phone, String type,
                     double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
