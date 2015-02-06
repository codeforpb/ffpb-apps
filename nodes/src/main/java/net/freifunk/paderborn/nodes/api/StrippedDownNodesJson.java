package net.freifunk.paderborn.nodes.api;

import com.fasterxml.jackson.annotation.*;
import com.noveogroup.android.log.*;

/**
 * Class to represent http://map.paderborn.freifunk.net/nodes.json without links
 */
@JsonIgnoreProperties({"links"})
public class StrippedDownNodesJson {
    public static final Logger LOGGER = LoggerManager.getLogger();
    Node[] nodes;
    MetaInformation meta;

    public StrippedDownNodesJson() {
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public MetaInformation getMeta() {
        return meta;
    }

    public void setMeta(MetaInformation meta) {
        this.meta = meta;
    }

    @JsonAnySetter
    public void setAny(String key, Object o) {
        LOGGER.w("Got Json: {} -> {}", key, o);
    }
}
