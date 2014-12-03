package net.freifunk.paderborn.ffpb_demo.json;

import com.fasterxml.jackson.annotation.*;
import com.noveogroup.android.log.*;

/**
 * Class to represent node flags.
 */
public class NodeFlags {
    public static final Logger LOGGER = LoggerManager.getLogger();
    boolean legacy, gateway, client, online;

    public NodeFlags() {
    }

    public boolean isLegacy() {
        return legacy;
    }

    public void setLegacy(boolean legacy) {
        this.legacy = legacy;
    }

    public boolean isGateway() {
        return gateway;
    }

    public void setGateway(boolean gateway) {
        this.gateway = gateway;
    }

    public boolean isClient() {
        return client;
    }

    public void setClient(boolean client) {
        this.client = client;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @JsonAnySetter
    public void setAny(String key, Object o) {
        LOGGER.w("Got Json: {} -> {}", key, o);
    }
}
