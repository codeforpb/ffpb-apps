package net.freifunk.paderborn.nodes.api;

import android.provider.*;

import com.fasterxml.jackson.annotation.*;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;
import com.noveogroup.android.log.*;

import net.freifunk.paderborn.nodes.persistence.*;

import java.util.*;

/**
 * Class to represent nodes.
 */
@DatabaseTable(tableName = Node.TABLE, daoClass = NodeDao.class)
public class Node {

    public static final Logger LOGGER = LoggerManager.getLogger();
    public static final String TABLE = "nodes";
    public static final String REMOTE_ID = "remoteId";
    public static final String _ID = BaseColumns._ID;
    public static final String NAME = "name";
    public static final String STARRED = "starred";
    public static final String ONLINE = "online";
    private static final int GEO_LAT_INDEX = 0,
            GEO_LON_INDEX = 1;
    @DatabaseField(generatedId = true, columnName = _ID)
    long _id;
    @DatabaseField(columnName = REMOTE_ID, unique = true)
    @JsonProperty("id")
    String remoteId;
    @DatabaseField(columnName = NAME)
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
    @DatabaseField(columnName = ONLINE)
    boolean online = false;
    @DatabaseField
    double lat;
    @DatabaseField
    double lon;
    @DatabaseField(columnName = STARRED, defaultValue = "0")
    boolean starred;
    private Date timeStamp;

    public Node() {
    }

    public boolean isLegacy() {
        return legacy;
    }

    public boolean isGateway() {
        return gateway;
    }

    public boolean isClient() {
        return client;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
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
        if (geo == null) {
            return;
        }
        this.lat = geo[GEO_LAT_INDEX];
        this.lon = geo[GEO_LON_INDEX];
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "_id=" + _id +
                ", remoteId='" + remoteId + '\'' +
                ", name='" + name + '\'' +
                ", firmware='" + firmware + '\'' +
                ", macs='" + macs + '\'' +
                ", clientcount=" + clientcount +
                ", legacy=" + legacy +
                ", gateway=" + gateway +
                ", client=" + client +
                ", online=" + online +
                ", lat=" + lat +
                ", lon=" + lon +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public void toggleStar() {
        starred = !starred;
    }
}
