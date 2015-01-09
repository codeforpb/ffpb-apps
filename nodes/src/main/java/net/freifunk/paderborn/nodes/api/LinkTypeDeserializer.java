package net.freifunk.paderborn.nodes.api;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;

/**
 * Custom Deserializer for {@link net.freifunk.paderborn.nodes.api.LinkType}s
 */
public class LinkTypeDeserializer extends JsonDeserializer<LinkType> {
    @Override
    public LinkType deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        return LinkType.fromName(parser.getValueAsString());
    }
}
