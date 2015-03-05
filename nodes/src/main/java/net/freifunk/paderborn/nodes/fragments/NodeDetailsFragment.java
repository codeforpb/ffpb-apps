package net.freifunk.paderborn.nodes.fragments;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;

import net.freifunk.paderborn.nodes.*;
import net.freifunk.paderborn.nodes.api.*;
import net.freifunk.paderborn.nodes.persistence.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

import java.sql.*;
import java.util.*;

/**
 * Displays the details of one node.
 */
@EFragment(R.layout.fragment_node_details)
public class NodeDetailsFragment extends Fragment {
    public static final String KEY_ARG_ID = "KEY_ARG_ID";
    public static final Logger LOGGER = LoggerFactory.getLogger(NodeDetailsFragment.class);
    @ViewById
    TextView textRemoteId, textClientCount, textFirmware, textMacs, textGateway, textClient, textCoordinates;

    @ColorRes
    int nodeOnline, nodeOffline, firmwareDeprecated, firmareRecent;

    @OptionsMenuItem
    MenuItem abStar;


    private Node mNode;
    private NodeDetails host;

    public static NodeDetailsFragment newInstance(long _id) {
        LOGGER.debug("newInstance({})", _id);

        Bundle args = new Bundle();
        args.putLong(KEY_ARG_ID, _id);

        NodeDetailsFragment fragment = new NodeDetailsFragment_();
        fragment.setArguments(args);

        LOGGER.debug("Created new MenuDetailFragment({})", _id);

        LOGGER.debug("newInstance({}) done", _id);
        return fragment;
    }

    @OptionsItem(R.id.abStar)
    void toggleStarred() {
        mNode.setStarred(!mNode.isStarred());
        showStar();
    }

    @AfterViews
    @Background
    void loadNode() {
        LOGGER.debug("loadNode()");
        long _id = getArguments().getLong(KEY_ARG_ID, -1L);
        if (_id == -1L) {
            throw new IllegalArgumentException("Id is -1L");
        }

        DatabaseHelper helper = DatabaseHelper.getInstance(getActivity());
        ConnectionSource connectionSource = helper.getConnectionSource();
        try {
            Dao<Node, ?> dao = DaoManager.createDao(connectionSource, Node.class);
            List<Node> nodes = dao.queryForEq(Node._ID, _id);
            if (nodes.size() != 1) {
                throw new IllegalStateException("Query for id returned size != 1");
            }
            mNode = nodes.get(0);
            LOGGER.debug("loadNode() done");
            showData();
        } catch (SQLException e) {
            LOGGER.error("Could not start fragment: {}", e);
        }
    }

    @UiThread
    void showData() {
        showNameAndStatus();
        showStar();
        showFirmware();
        textRemoteId.setText(mNode.getRemoteId());
        textClientCount.setText("" + mNode.getClientcount());
        textMacs.setText(mNode.getMacs());
        textGateway.setText(mNode.isGateway() + "");
        textClient.setText(mNode.isClient() + "");
        textCoordinates.setText(mNode.getLat() + ", " + mNode.getLon());
        LOGGER.debug("showData() done");
    }

    private void showFirmware() {
        textFirmware.setText(mNode.getFirmware());
        final int firmwareTextColor;
        if (mNode.isLegacy()) {
            firmwareTextColor = firmwareDeprecated;
        } else {
            firmwareTextColor = firmareRecent;
        }
        textFirmware.setTextColor(firmwareTextColor);
    }

    private void showNameAndStatus() {
        int onlineStatusColor = (mNode.isOnline()) ? nodeOnline : nodeOffline;
        host.setToolbarInfos(mNode.getName(), onlineStatusColor);
    }

    @Override
    public void onPause() {
        super.onPause();
        writeBackChanges();
    }

    private void writeBackChanges() {
        LOGGER.debug("writeBackChanges()");
        try {
            DatabaseHelper helper = DatabaseHelper.getInstance(getActivity());
            ConnectionSource connectionSource = helper.getConnectionSource();
            Dao<Node, ?> dao = DaoManager.createDao(connectionSource, Node.class);
            dao.update(mNode);
            LOGGER.info("Updated DB entry.");
        } catch (SQLException e) {
            LOGGER.error("Could not save changes.", e);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        showStar();
    }

    private void showStar() {
        if (abStar == null || mNode == null) {
            return;
        }

        if (mNode.isStarred()) {
            abStar.setIcon(R.drawable.ic_action_important);
        } else {
            abStar.setIcon(R.drawable.ic_action_not_important);
        }
    }

    public NodeDetails getHost() {
        return host;
    }

    public void setHost(NodeDetails host) {
        this.host = host;
    }
}