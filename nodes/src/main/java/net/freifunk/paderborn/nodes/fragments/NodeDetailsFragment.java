package net.freifunk.paderborn.nodes.fragments;

import android.os.*;
import android.support.v4.app.*;
import android.widget.*;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;

import net.freifunk.paderborn.nodes.*;
import net.freifunk.paderborn.nodes.api.*;
import net.freifunk.paderborn.nodes.persistence.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import java.sql.*;
import java.util.*;

/**
 * Created by ljan on 07.02.15.
 */
@EFragment(R.layout.fragment_node_details)
public class NodeDetailsFragment extends Fragment {
    public static final String KEY_ARG_ID = "KEY_ARG_ID";
    public static final Logger LOGGER = LoggerFactory.getLogger(NodeDetailsFragment.class);
    @ViewById
    TextView textName;
    @ViewById
    CheckBox checkBoxStarred;
    private Node mNode;

    public static Fragment newInstance(long _id) {
        LOGGER.debug("newInstance({})", _id);

        Bundle args = new Bundle();
        args.putLong(KEY_ARG_ID, _id);

        NodeDetailsFragment fragment = new NodeDetailsFragment_();
        fragment.setArguments(args);

        LOGGER.debug("Created new MenuDetailFragment({})", _id);

        LOGGER.debug("newInstance({}) done", _id);
        return fragment;
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
        textName.setText(mNode.getName());
        checkBoxStarred.setChecked(mNode.isStarred());
        LOGGER.debug("showData() done");
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
            mNode.setStarred(checkBoxStarred.isChecked());
            dao.update(mNode);
            LOGGER.info("Updated DB entry.");
        } catch (SQLException e) {
            LOGGER.error("Could not save changes.", e);
        }

    }
}