package net.freifunk.paderborn.nodes.fragments;

import android.database.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.view.*;
import android.widget.*;

import net.freifunk.paderborn.nodes.*;
import net.freifunk.paderborn.nodes.api.*;
import net.freifunk.paderborn.nodes.sync.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.rest.*;
import org.slf4j.*;

import java.sql.SQLException;

/**
 * Fragment to display a list of nodes
 */
@EFragment(R.layout.fragment_nodes)
public class NodesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ID = 0;
    public static final Logger LOGGER = LoggerFactory.getLogger(NodesFragment.class);
    @RestService
    NodesJsonApi mNodesJsonApi;
    @ViewById
    ListView list;
    @ViewById
    View empty;
    private String[] mProjection = {Node.NAME, Node.STARRED, Node.REMOTE_ID, Node._ID};
    private SimpleCursorAdapter mAdapter;

    @AfterViews
    @Trace
    void demoLoad() {
        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_node,
                null,
                mProjection, new int[]{R.id.textName, R.id.imageStarred}, 0);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (R.id.imageStarred == view.getId()) {
                    int boolAsInt = cursor.getInt(columnIndex);
                    boolean isStarred = (boolAsInt == 1);
                    if (isStarred) {
                        ((ImageView) view).setImageResource(R.drawable.ic_action_important);
                    } else {
                        ((ImageView) view).setImageResource(R.drawable.ic_action_not_important);
                    }
                    return true;
                }
                return false;
            }
        });
        list.setEmptyView(empty);
        list.setAdapter(mAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @UiThread
    void showError(SQLException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String selection = Node.NAME + " IS NOT NULL AND " + Node.NAME + " != ''";
        CursorLoader loader = new CursorLoader(
                this.getActivity(),
                FfpbUriMatcher.NODES_URI,
                mProjection,
                selection,
                null,
                null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    @ItemClick(android.R.id.list)
    void itemClick(int pos) {
        long _id = mAdapter.getItemId(pos);
        NodeDetails_.intent(this).nodeId(_id).start();
        LOGGER.debug("Started details..");
    }
}
