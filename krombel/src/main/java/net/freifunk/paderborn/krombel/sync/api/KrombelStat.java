package net.freifunk.paderborn.krombel.sync.api;

import android.provider.*;

import com.fasterxml.jackson.databind.annotation.*;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

import java.util.*;

/**
 * A stat from krombel's dashboard
 */
@DatabaseTable
public class KrombelStat {
    public static final String TIMESTAMP = "timestamp";
    public static final String TYPE = "type";
    public static final String COUNT = "count";
    @DatabaseField(generatedId = true, columnName = BaseColumns._ID, canBeNull = false)
    private long _id;
    @DatabaseField(columnName = COUNT, canBeNull = false)
    private int count;
    @DatabaseField(columnName = TIMESTAMP, canBeNull = false, dataType = DataType.DATE_LONG)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date timestamp;
    @DatabaseField(columnName = TYPE, canBeNull = false, dataType = DataType.ENUM_STRING)
    private KrombelStatType type;

    public KrombelStat() {

    }

    public KrombelStat(int count, Date timestamp, KrombelStatType type) {
        this.count = count;
        this.timestamp = timestamp;
        this.type = type;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public KrombelStatType getType() {
        return type;
    }

    public void setType(KrombelStatType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "KrombelStat{" +
                "_id=" + _id +
                ", count=" + count +
                ", timestamp=" + timestamp +
                ", type=" + type +
                '}';
    }
}
