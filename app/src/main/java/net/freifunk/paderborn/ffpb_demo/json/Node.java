package net.freifunk.paderborn.ffpb_demo.json;

import com.fasterxml.jackson.annotation.*;
import com.noveogroup.android.log.*;

/**
 * Class to represent nodes.
 */
public class Node {

    public static final Logger LOGGER = LoggerManager.getLogger();
    private static final int GEO_LAT_INDEX = 0,
            GEO_LON_INDEX = 1;
    String firmware, name, id, macs;
    NodeFlags flags;
    double[] geo;
    int clientcount;

    double lat, lon;

    public Node() {
    }

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

    public void setGeo(double[] geo) {
        if (geo != null && geo.length != 2) {
            throw new IllegalArgumentException("Geo coordinates must be 'null' or have exact two coordinates.");
        }
        this.geo = geo;
    }

    public NodeFlags getFlags() {
        return flags;
    }

    public void setFlags(NodeFlags flags) {
        this.flags = flags;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMacs() {
        return macs;
    }

    public void setMacs(String macs) {
        this.macs = macs;
    }

    public int getClientcount() {
        return clientcount;
    }

    public void setClientcount(int clientcount) {
        this.clientcount = clientcount;
    }

    @JsonAnySetter
    public void setAny(String key, Object o) {
        LOGGER.w("Got Json: {} -> {}", key, o);
    }
}
