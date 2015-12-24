package addresses;

public class Coords {
    String address;
    float longitude;
    float latitude;
    
    public Coords() {}

    public Coords(String address, float longitude, float latitude) {
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Coords{" + "address=" + address + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }
    
    
}
