package net.freifunk.paderborn.ffpb_demo.json;

import org.androidannotations.annotations.rest.*;
import org.springframework.http.converter.json.*;

/**
 * An api-interface to get some freifunk-data. Other sources (non-json) include:
 *
 * Krombel's dashboard
 * <ul>
 *     <li>http://counts.paderborn.freifunk.net/aktNodecount.json</li>
 *     <li>http://counts.paderborn.freifunk.net/aktClientcount.json</li>
 *     <li>http://counts.paderborn.freifunk.net/maxNodecount.json</li>
 *     <li>http://counts.paderborn.freifunk.net/maxClientcount.json</li>
 * </ul>
 */
@Rest(rootUrl = "http://map.paderborn.freifunk.net/nodes.json", converters = MappingJackson2HttpMessageConverter.class)
public interface Api {

    public NodesJson getNodesJson();


}
