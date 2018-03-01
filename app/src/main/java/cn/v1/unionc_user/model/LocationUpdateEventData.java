package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/2/27.
 */

public class LocationUpdateEventData {

    private String poiName;

    private double lat;

    private double lon;

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

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
}
