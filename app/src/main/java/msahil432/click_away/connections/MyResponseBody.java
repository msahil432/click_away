package msahil432.click_away.connections;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.ResponseBody;

public class MyResponseBody implements Serializable{

    @SerializedName("hospitals")
    @Expose
    private List<Hospital> hospitals = null;
    @SerializedName("bloodbanks")
    @Expose
    private List<Bloodbank> bloodbanks = null;
    @SerializedName("chemist")
    @Expose
    private List<Chemist> chemist = null;

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public List<Bloodbank> getBloodbanks() {
        return bloodbanks;
    }

    public void setBloodbanks(List<Bloodbank> bloodbanks) {
        this.bloodbanks = bloodbanks;
    }

    public List<Chemist> getChemist() {
        return chemist;
    }

    public void setChemist(List<Chemist> chemist) {
        this.chemist = chemist;
    }

    public static class Bloodbank implements Serializable {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("contact")
        @Expose
        private Long contact;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("pincode")
        @Expose
        private Long pincode;
        @SerializedName("loc")
        @Expose
        private List<Double> loc = null;
        @SerializedName("__v")
        @Expose
        private Long v;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Long getContact() {
            return contact;
        }

        public void setContact(Long contact) {
            this.contact = contact;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getPincode() {
            return pincode;
        }

        public void setPincode(Long pincode) {
            this.pincode = pincode;
        }

        public List<Double> getLoc() {
            return loc;
        }

        public void setLoc(List<Double> loc) {
            this.loc = loc;
        }

        public Long getV() {
            return v;
        }

        public void setV(Long v) {
            this.v = v;
        }

    }

    public static class Chemist implements Serializable{

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("contact")
        @Expose
        private Long contact;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("pincode")
        @Expose
        private Long pincode;
        @SerializedName("loc")
        @Expose
        private List<Double> loc = null;
        @SerializedName("__v")
        @Expose
        private Long v;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Long getContact() {
            return contact;
        }

        public void setContact(Long contact) {
            this.contact = contact;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getPincode() {
            return pincode;
        }

        public void setPincode(Long pincode) {
            this.pincode = pincode;
        }

        public List<Double> getLoc() {
            return loc;
        }

        public void setLoc(List<Double> loc) {
            this.loc = loc;
        }

        public Long getV() {
            return v;
        }

        public void setV(Long v) {
            this.v = v;
        }

    }

    public static class Hospital implements Serializable{

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("contact")
        @Expose
        private Long contact;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("pincode")
        @Expose
        private Long pincode;
        @SerializedName("loc")
        @Expose
        private List<Double> loc = null;
        @SerializedName("__v")
        @Expose
        private Long v;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Long getContact() {
            return contact;
        }

        public void setContact(Long contact) {
            this.contact = contact;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getPincode() {
            return pincode;
        }

        public void setPincode(Long pincode) {
            this.pincode = pincode;
        }

        public List<Double> getLoc() {
            return loc;
        }

        public void setLoc(List<Double> loc) {
            this.loc = loc;
        }

        public Long getV() {
            return v;
        }

        public void setV(Long v) {
            this.v = v;
        }

    }
}