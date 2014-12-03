package net.freifunk.paderborn.ffpb_demo.json;

import org.androidannotations.annotations.rest.*;
import org.springframework.http.converter.json.*;

/**
 * An api-interface to get some freifunk-data. Other sources (non-json) include:
 *
 * Krombel's dashboard. Date is available via "last modified":
 * <ul><li>http://counts.paderborn.freifunk.net/aktNodecount.txt</li>
 * <li>http://counts.paderborn.freifunk.net/aktClientcount.txt</li>
 * <li>http://counts.paderborn.freifunk.net/maxNodecount.txt</li>
 * <li>http://counts.paderborn.freifunk.net/maxClientcount.txt</li>
 * </ul>
 */
@Rest(rootUrl = "http://map.paderborn.freifunk.net/nodes.json", converters = MappingJackson2HttpMessageConverter.class)
public interface Api {

    public NodesJson getNodesJson();


}
