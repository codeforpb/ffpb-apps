package net.freifunk.paderborn.ffpb_demo.apis.krombel_dash;

import org.androidannotations.annotations.rest.*;
import org.springframework.http.converter.json.*;

/**
 * API to get some data from Krombel's dashboard
 */
@Rest(rootUrl = "http://counts.paderborn.freifunk.net/", converters = MappingJackson2HttpMessageConverter.class)
public interface KrombelDashApi {
    @Get("aktNodecount.json")
    public KrombelStat currentNodeCount();

    @Get("aktClientcount.json")
    public KrombelStat currentClientCount();

    @Get("maxNodecount.json")
    public KrombelStat maximumNodeCount();

    @Get("maxClientcount.json")
    public KrombelStat maximumClientCount();
}
