package net.freifunk.paderborn.nodes.api;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

/**
 * Class to represent http://map.paderborn.freifunk.net/nodes.json
 */
public class NodesJson {
    public static final Logger LOGGER = LoggerManager.getLogger();
    Link[] links;
    Node[] nodes;
    MetaInformation meta;

    public NodesJson() {

    }

    public Link[] getLinks() {
        return links;
    }

    public void setLinks(Link[] links) {
        this.links = links;
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
