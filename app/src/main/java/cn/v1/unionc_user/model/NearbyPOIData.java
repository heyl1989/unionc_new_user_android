package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/2/27.
 */

public class NearbyPOIData{

    private String POIName;
    private String address;
    private double lat;
    private double lon;
    private double distance;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getPOIName() {
        return POIName;
    }

    public void setPOIName(String POIName) {
        this.POIName = POIName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
