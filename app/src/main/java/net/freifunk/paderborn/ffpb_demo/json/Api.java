package net.freifunk.paderborn.ffpb_demo.json;

import org.androidannotations.annotations.rest.*;
import org.springframework.http.converter.json.*;

/**
 *
 */
@Rest(rootUrl = "http://map.paderborn.freifunk.net/nodes.json", converters = MappingJackson2HttpMessageConverter.class)
public interface Api {

    public NodesJson getNodesJson();

}
