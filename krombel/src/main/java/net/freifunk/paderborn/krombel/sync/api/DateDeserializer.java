package net.freifunk.paderborn.krombel.sync.api;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import org.slf4j.*;

import java.io.*;
import java.util.*;

/**
 * FIXME doc
 */
public class DateDeserializer extends JsonDeserializer<Date> {
    Logger logger = LoggerFactory.getLogger(DateDeserializer.class);

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String timestamp = jp.getText().trim();

        try {
            return new Date(Long.valueOf(timestamp + "000"));
        } catch (NumberFormatException e) {
            logger.warn("Unable to deserialize timestamp: " + timestamp, e);
            return null;
        }
    }
}