package net.freifunk.paderborn.ffpb_demo.json;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

/**
 * Class to represent json-API metainformation
 */
public class MetaInformation {
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="CET")
    Date timestamp;
}
