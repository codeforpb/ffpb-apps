package net.freifunk.paderborn.nodes.fragments;

import android.database.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.text.*;
import android.view.*;
import android.widget.*;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;

import net.freifunk.paderborn.nodes.*;
import net.freifunk.paderborn.nodes.api.*;
import net.freifunk.paderborn.nodes.persistence.*;
import net.freifunk.paderborn.nodes.sync.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.res.*;
import org.androidannotations.annotations.rest.*;
import org.slf4j.*;

import java.sql.SQLException;
import java.util.*;

/**
 * Fragment to display a list of nodes. Hosted in Nodes-Activity
 * @see net.freifunk.paderborn.nodes.Nodes
 */
@EFragment(R.layout.fragment_nodes)
public class NodesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ID = 0;
    public static final Logger LOGGER = LoggerFactory.getLogger(NodesFragment.class);
    public static final String KEY_SEARCH_TEXT = "SEARCH_TEXT";
    public static final int COLUMN_ID = 4;
    public static final int COLUMN_NAME = 0;
    private static final int ONLINE_INDEX = 2;
    @RestService
    NodesJsonApi mNodesJsonApi;
    @ViewById
    ListView list;
    @ViewById
    View empty;

    @ColorRes
    int nodeOnline, nodeOffline;
    private String[] mProjection = {Node.NAME, Node.STARRED, Node.ONLINE, Node.REMOTE_ID, Node._ID};
    private SimpleCursorAdapter mAdapter;

    @AfterViews
    @Trace
    void demoLoad() {
        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_node,
                null,
                mProjection, new int[]{R.id.textName, R.id.imageStarred}, 0);
        mAdapter.setViewBinder(new NodesListItemViewBinder());
        list.setEmptyView(empty);
        list.setAdapter(mAdapter);
        getLoaderManager().initLoader(LOADER_ID, Bundle.EMPTY, this);
    }


    @UiThread
    void showError(SQLException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        final String query = args.getString(KEY_SEARCH_TEXT, "");
        final String nonEmptyNameQuery =
                (TextUtils.isEmpty(query))
                        ? "%_%"
                        : "%" + query + "%";
        final String selection = Node.NAME + " LIKE ?";
        final String[] selectionArgs = new String[]{nonEmptyNameQuery};
        CursorLoader loader = new CursorLoader(
                this.getActivity(),
                FfpbUriMatcher.NODES_URI,
                mProjection,
                selection,
                selectionArgs,
                null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.changeCursor(null);
    }

    @ItemClick(android.R.id.list)
    void itemClick(int pos) {
        long _id = mAdapter.getItemId(pos);
        NodeDetails_.intent(this).nodeId(_id).start();
        LOGGER.debug("Started details..");
    }

    public void search(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SEARCH_TEXT, query);
        getLoaderManager().restartLoader(LOADER_ID, bundle, this);
    }

    private void toggleStarred(long _id) {
        DatabaseHelper helper = DatabaseHelper.getInstance(getActivity());
        ConnectionSource connectionSource = helper.getConnectionSource();
        try {
            Dao<Node, ?> dao = DaoManager.createDao(connectionSource, Node.class);
            List<Node> nodes = dao.queryForEq(Node._ID, _id);
            if (nodes.size() != 1) {
                throw new IllegalStateException("Query for id returned size != 1");
            }
            Node node = nodes.get(0);
            LOGGER.debug("node loaded");
            node.toggleStar();
            dao.update(node);
        } catch (SQLException e) {
            LOGGER.error("Could not start fragment: {}", e);
        }
        getActivity().getContentResolver().notifyChange(FfpbUriMatcher.NODES_URI, null, false);
    }

    private class NodesListItemViewBinder implements SimpleCursorAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, final Cursor cursor, int columnIndex) {
            if (R.id.textName == view.getId()) {
                bindName((TextView) view, cursor, columnIndex);
                return true;
            }
            final long _id = cursor.getLong(COLUMN_ID);
            final String name = cursor.getString(COLUMN_NAME);
            if (R.id.imageStarred == view.getId()) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toggleStarred(_id);
                    }
                });
                bindFavoriteState((ImageView) view, cursor, columnIndex);
                return true;
            }
            return false;
        }

        private void bindFavoriteState(ImageView view, Cursor cursor, int columnIndex) {
            int boolAsInt = cursor.getInt(columnIndex);
            boolean isStarred = (boolAsInt == 1);
            if (isStarred) {
                view.setImageResource(R.drawable.ic_action_important);
            } else {
                view.setImageResource(R.drawable.ic_action_not_important);
            }
        }

        private void bindName(TextView view, Cursor cursor, int columnIndex) {
            view.setText(cursor.getString(columnIndex));
            boolean isOnline = (cursor.getInt(ONLINE_INDEX) == 1);
            if (isOnline) {
                view.setTextColor(nodeOnline);
            } else {
                view.setTextColor(nodeOffline);
            }
        }
    }
}
