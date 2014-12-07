package net.freifunk.paderborn.krombel.sync.api;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

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
