package net.freifunk.paderborn.nodes;


import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;

import net.freifunk.paderborn.nodes.fragments.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

/**
 * Created by ljan on 07.02.15.
 */
@EActivity(R.layout.activity_node_details)
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = NodeDetailsFragment.newInstance(nodeId);
        ft.replace(R.id.fragment, fragment, "fragment");
        ft.commit();
        LOGGER.debug("bindFragment() done");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OptionsItem(android.R.id.home)
    void navUp() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
