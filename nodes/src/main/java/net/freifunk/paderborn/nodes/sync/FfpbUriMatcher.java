package net.freifunk.paderborn.nodes.sync;

import android.content.*;
import android.net.*;

/**
 * Path structure: (authority: net.freifunk.paderborn)
 * /nodes - list of nodes
 * /nodes/(#|*) - single node via (id|remote id)
 * /nodes/(#|*)/field - value of field on single node identified via (id|remote id) [only important!]
 */
public class FfpbUriMatcher extends UriMatcher {

    public static final int MATCH_ROOT = 0;
    public static final int MATCH_NODES = 1;
    public static final int MATCH_NODE_VIA_LOCAL_ID = 2;
    public static final int MATCH_NODE_VIA_REMOTE_ID = 3;
    private static final String PATH_DIVIDER = "/",
            WILDCARD_NUMBER = "#",
            WILDCARD_TEXT = "*",
            PATH_NODES = "nodes",
            AUTHORITY = "net.freifunk.paderborn";
    public static final Uri NODES_URI = Uri.parse("content://" + AUTHORITY + PATH_DIVIDER + PATH_NODES);

    /**
     * Creates the FfpbUriMatcher.
     */
    public FfpbUriMatcher() {
        super(MATCH_ROOT);
        addURI(AUTHORITY, PATH_NODES, MATCH_NODES);
        addURI(AUTHORITY, PATH_NODES + PATH_DIVIDER + WILDCARD_NUMBER, MATCH_NODE_VIA_LOCAL_ID);
        addURI(AUTHORITY, PATH_NODES + PATH_DIVIDER + WILDCARD_TEXT, MATCH_NODE_VIA_REMOTE_ID);
    }
}
