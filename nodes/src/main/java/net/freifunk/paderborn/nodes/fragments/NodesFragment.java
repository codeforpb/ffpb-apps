package net.freifunk.paderborn.nodes.fragments;

import android.support.v4.app.*;

import net.freifunk.paderborn.nodes.*;
import net.freifunk.paderborn.nodes.api.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.*;
import org.slf4j.*;

/**
 * Fragment to display a list of nodes
 */
@EFragment(R.layout.fragment_nodes)
public class NodesFragment extends ListFragment {
    public static final Logger LOGGER = LoggerFactory.getLogger(NodesFragment.class);
    @RestService
    NodesJsonApi mNodesJsonApi;

    @AfterViews
    @Trace
    @Background
    void demoLoad() {
        NodesJson nodesJson = mNodesJsonApi.getNodesJson();
        if (nodesJson == null) {
            LOGGER.debug("Got null!");
        } else {
            MetaInformation meta = nodesJson.getMeta();
            int linkLength = (nodesJson.getLinks() != null) ? nodesJson.getLinks().length : -1;
            int nodesLength = (nodesJson.getNodes() != null) ? nodesJson.getNodes().length : -1;
            LOGGER.debug("Got {} links and {} nodes!", linkLength, nodesLength);
            LOGGER.debug("Metadata is {}", meta);
        }

    }

}
