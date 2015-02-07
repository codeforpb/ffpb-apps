package net.freifunk.paderborn.nodes.fragments;

import android.database.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.view.*;
import android.widget.*;

import net.freifunk.paderborn.nodes.*;
import net.freifunk.paderborn.nodes.api.*;
import net.freifunk.paderborn.nodes.persistence.*;
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
    public static final Logger LOGGER = LoggerFactory.getLogger(NodesFragment.class);
    public static final int LOADER_ID = 0;
    @RestService
    NodesJsonApi mNodesJsonApi;
    @ViewById
    ListView list;
    @ViewById
    View empty;
    private DatabaseHelper mDatabaseHelper;
    private String[] mProjection = {Node.NAME, Node.REMOTE_ID, Node._ID};
    private CursorAdapter mAdapter;
    private Cursor mActiveCursor;

    @AfterViews
    @Trace
    void demoLoad() {
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                mProjection, new int[]{android.R.id.text1}, 0);
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
        mActiveCursor = cursor;
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    @ItemClick(android.R.id.list)
    void itemClick(int pos) {
        if (mActiveCursor != null) {
            mActiveCursor.moveToPosition(pos);
            String string = mActiveCursor.getString(0) + " (" + mActiveCursor.getString(1) + ")";
            Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
        }
    }
}
