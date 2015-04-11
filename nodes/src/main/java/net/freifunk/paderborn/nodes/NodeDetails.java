package net.freifunk.paderborn.nodes;


import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;

import net.freifunk.paderborn.nodes.fragments.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

/**
 * Host for a NodeDetailsFragment
 */
@EActivity(R.layout.activity_node_details)
public class NodeDetails extends ActionBarActivity {
    @Extra(value = NodeDetailsFragment.KEY_ARG_ID)
    long nodeId;

    Logger LOGGER = LoggerFactory.getLogger(NodeDetails.class);


    @AfterViews
    void bindFragment() {
        LOGGER.warn("bindFragment()");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        NodeDetailsFragment fragment = NodeDetailsFragment.newInstance(nodeId);
        fragment.setHost(this);
        ft.replace(R.id.fragment, fragment, "fragment");
        ft.commit();
        LOGGER.debug("bindFragment() done");
    }


    @OptionsItem(android.R.id.home)
    void navUp() {
        NavUtils.navigateUpFromSameTask(this);
    }


}
