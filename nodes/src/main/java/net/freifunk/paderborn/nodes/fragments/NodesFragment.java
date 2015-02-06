package net.freifunk.paderborn.nodes.fragments;

import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;

import net.freifunk.paderborn.nodes.*;
import net.freifunk.paderborn.nodes.api.*;
import net.freifunk.paderborn.nodes.persistence.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.*;
import org.slf4j.*;

import java.sql.*;
import java.util.*;

/**
 * Fragment to display a list of nodes
 */
@EFragment(R.layout.fragment_nodes)
public class NodesFragment extends Fragment {
    public static final Logger LOGGER = LoggerFactory.getLogger(NodesFragment.class);
    @RestService
    NodesJsonApi mNodesJsonApi;
    @ViewById
    ListView list;
    @ViewById
    View empty;
    private DatabaseHelper mDatabaseHelper;

    @AfterViews
    @Trace
    @Background
    void demoLoad() {
        mDatabaseHelper = OpenHelperManager.getHelper(getActivity(),
                DatabaseHelper.class);
        try {
            Dao<Node, ?> dao = DaoManager.createDao(mDatabaseHelper.getConnectionSource(), Node.class);
            List<Node> nodes = dao.queryForAll();
            showNodes(nodes);
        } catch (SQLException e) {
            showError(e);
        }
    }

    @UiThread
    void showNodes(List<Node> nodesList) {
        TemporaryAdapter adapter = new TemporaryAdapter(getActivity(), nodesList);
        list.setAdapter(adapter);
        list.setEmptyView(empty);
    }

    @UiThread
    void showError(SQLException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

}
