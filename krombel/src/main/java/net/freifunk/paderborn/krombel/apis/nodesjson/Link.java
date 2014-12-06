package net.freifunk.paderborn.krombel.apis.nodesjson;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

/**
 * Class to represent Links.
 */
public class Link {
    public static final Logger LOGGER = LoggerManager.getLogger();
    String id, client, quality;
    int target, source;

    public Link() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @JsonAnySetter
    public void setAny(String key, Object o) {
        LOGGER.w("Got Json: {} -> {}", key, o);
    }
}
