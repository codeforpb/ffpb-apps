package net.freifunk.paderborn.ffpb_demo.apis.krombel_dash;

import java.util.*;

/**
 * Created by ljan on 03.12.14.
 */
public class KrombelStat {
    int count;
    Date timestamp;
    private KrombelStatType type;

    public KrombelStat() {

    }

    public KrombelStat(int count, Date timestamp, KrombelStatType type) {
        this.count = count;
        this.timestamp = timestamp;
        this.type = type;
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
