package cool.test.mycollege.MyDataTypes;

/**
 * Created by Vipin soni on 03-08-2018.
 */

public class map_datatype {

    float lat,lon;
    String place_name,short_desc;

    public map_datatype(float lat, float lon, String place_name, String short_desc) {
        this.lat = lat;
        this.lon = lon;
        this.place_name = place_name;
        this.short_desc = short_desc;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }
}
