package net.freifunk.paderborn.nodes.api;

import android.provider.*;

import com.fasterxml.jackson.annotation.*;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;
import com.noveogroup.android.log.*;

/**
 * Class to represent nodes.
 */
@DatabaseTable(tableName = Node.NODES)
public class Node {

    public static final Logger LOGGER = LoggerManager.getLogger();
    public static final String NODES = "nodes";
    private static final int GEO_LAT_INDEX = 0,
            GEO_LON_INDEX = 1;
    @DatabaseField(generatedId = true, columnName = BaseColumns._ID)
    long _id;
    @DatabaseField(unique = true)
    String remoteId;
    @DatabaseField
    String name;
    @DatabaseField
    String firmware;
    @DatabaseField
    String macs;
    @DatabaseField
    int clientcount = 0;
    @DatabaseField
    boolean legacy = false;
    @DatabaseField
    boolean gateway = false;
    @DatabaseField
    boolean client = false;
    @DatabaseField
    boolean online = false;
    @DatabaseField(generatedId = true)
    double lat;
    @DatabaseField(generatedId = true)
    double lon;

    public Node() {
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

    public void setGeo(double[] geo) {
        if (geo != null && geo.length != 2) {
            throw new IllegalArgumentException("Geo coordinates must be 'null' or have exact two coordinates.");
        }
        this.lat = geo[GEO_LAT_INDEX];
        this.lon = geo[GEO_LON_INDEX];
    }

    public void setFlags(NodeFlags flags) {
        if (flags == null) {
            return;
        }
        this.legacy = flags.isLegacy();
        this.gateway = flags.isGateway();
        this.client = flags.isClient();
        this.online = flags.isOnline();
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

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
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
