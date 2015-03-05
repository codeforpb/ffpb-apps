package net.freifunk.paderborn.nodes;


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
@OptionsMenu(R.menu.star)
public class NodeDetails extends ActionBarActivity {
    @Extra(value = NodeDetailsFragment.KEY_ARG_ID)
    long nodeId;
    @ViewById
    Toolbar toolbar;

    Logger LOGGER = LoggerFactory.getLogger(NodeDetails.class);


    @AfterViews
    void bindFragment() {
        LOGGER.warn("bindFragment()");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    public void setToolbarInfos(String name, int onlineStatusColor) {
        toolbar.setTitle(name);
        toolbar.setBackgroundColor(onlineStatusColor);
    }
}
