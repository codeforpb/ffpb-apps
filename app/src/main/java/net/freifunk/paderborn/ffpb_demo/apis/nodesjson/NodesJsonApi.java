package net.freifunk.paderborn.ffpb_demo.apis.nodesjson;

import org.androidannotations.annotations.rest.*;
import org.springframework.http.converter.json.*;

/**
 * An api-interface to get some freifunk-data from the map page.
 */
@Rest(rootUrl = "http://map.paderborn.freifunk.net/nodes.json", converters = MappingJackson2HttpMessageConverter.class)
public interface NodesJsonApi {

    @Get("")
    public NodesJson getNodesJson();

}
