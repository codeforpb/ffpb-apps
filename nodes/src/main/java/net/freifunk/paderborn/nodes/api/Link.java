package net.freifunk.paderborn.nodes.api;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.j256.ormlite.field.*;
import com.j256.ormlite.logger.*;
import com.j256.ormlite.table.*;

/**
 * Class to represent Links.
 */
@DatabaseTable(tableName = "links")
public class Link {
    public static final Logger LOGGER = LoggerFactory.getLogger(Link.class);

    @DatabaseField(generatedId = true)
    long _id;
    @DatabaseField(generatedId = true)
    String remoteId;
    @DatabaseField(generatedId = true)
    String client;
    @DatabaseField(generatedId = true)
    String quality;
    @DatabaseField(generatedId = true)
    int target;
    @DatabaseField(generatedId = true)
    int source;

    @DatabaseField(generatedId = true)
    @JsonDeserialize(using = LinkTypeDeserializer.class)
    LinkType type;

    public Link() {

    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
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

    public LinkType getType() {
        return type;
    }

    public void setType(LinkType type) {
        this.type = type;
    }

    @JsonAnySetter
    public void setAny(String key, Object o) {
        LOGGER.warn("Got Json: {} -> {}", key, o);
    }

    @Override
    public String toString() {
        return "Link{" +
                "id='" + remoteId + '\'' +
                ", client='" + client + '\'' +
                ", quality='" + quality + '\'' +
                ", target=" + target +
                ", source=" + source +
                ", type=" + type +
                '}';
    }
}
