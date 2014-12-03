package net.freifunk.paderborn.ffpb_demo.apis.nodesjson;

import com.fasterxml.jackson.annotation.*;
import com.noveogroup.android.log.*;

import java.util.*;

/**
 * Class to represent json-API metainformation
 */
public class MetaInformation {
    public static final Logger LOGGER = LoggerManager.getLogger();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "CET")
    Date timestamp;

    public MetaInformation() {

    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @JsonAnySetter
    public void setAny(String key, Object o) {
        LOGGER.w("Got Json: {} -> {}", key, o);
    }
}
