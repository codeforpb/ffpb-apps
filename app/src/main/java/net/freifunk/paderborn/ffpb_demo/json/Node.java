package net.freifunk.paderborn.ffpb_demo.json;

/**
 * Class to represent nodes.
 */
public class Node {

    String firmware, name, id, macs;
    NodeFlags flags;
    double[] geo;
    private static final int GEO_LAT_INDEX = 0,
            GEO_LON_INDEX = 1;
    int clientcount;

    double lat, lon;

    public double getLat() {
        return geo[GEO_LAT_INDEX];
    }

    public void setLat(double lat) {
        this.geo[GEO_LAT_INDEX] = lat;
    }

    public double getLon() {
        return geo[GEO_LON_INDEX];
    }

    public void setLon(double lon) {
        this.geo[GEO_LON_INDEX] = lon;
    }

    public void setGeo(double[] geo){
        if(geo != null && geo.length != 2){
            throw  new IllegalArgumentException("Geo coordinates must be 'null' or have exact two coordinates.");
        }
        this.geo = geo;
    }
}
