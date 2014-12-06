package net.freifunk.paderborn.krombel.apis.nodesjson;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * An api-interface to get some freifunk-data from the map page.
 */
@Rest(rootUrl = "http://map.paderborn.freifunk.net/nodes.json", converters = MappingJackson2HttpMessageConverter.class)
public interface NodesJsonApi {

    @Get("")
    public NodesJson getNodesJson();

}
