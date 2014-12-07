package net.freifunk.paderborn.krombel.sync.api;

import android.provider.BaseColumns;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * A stat from krombel's dashboard
 */
@DatabaseTable
public class KrombelStat {
    @DatabaseField(generatedId = true, columnName = BaseColumns._ID, canBeNull = false)
    private long _id;
    @DatabaseField(columnName = "count", canBeNull = false)
    private int count;
    @DatabaseField(columnName = "timestamp", canBeNull = false, dataType = DataType.DATE_LONG)
    private Date timestamp;
    @DatabaseField(columnName = "type", canBeNull = false, dataType = DataType.ENUM_STRING)
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
}
