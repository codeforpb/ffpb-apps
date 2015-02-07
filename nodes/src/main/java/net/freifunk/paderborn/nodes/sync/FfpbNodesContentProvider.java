package net.freifunk.paderborn.nodes.sync;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.text.*;

import net.freifunk.paderborn.nodes.api.*;
import net.freifunk.paderborn.nodes.persistence.*;

import org.androidannotations.annotations.*;

/**
 * A content provider for the Sync Adapter. Right now most things are stubbed.
 * <p/>
 * <p/>
 * http://developer.android.com/guide/topics/providers/content-provider-creating.html
 */
@EProvider
public class FfpbNodesContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new FfpbUriMatcher();
    private DatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Node.TABLE);

        int match = sUriMatcher.match(uri);
        switch (match) {
            case FfpbUriMatcher.MATCH_NODES:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = Node.STARRED + " DESC, " + Node.NAME + " ASC ";
                }
                break;
            case FfpbUriMatcher.MATCH_NODE_VIA_LOCAL_ID:
                selection = selection + " " + Node._ID + uri.getLastPathSegment();
                break;
            case FfpbUriMatcher.MATCH_NODE_VIA_REMOTE_ID:
                selection = selection + " " + Node.REMOTE_ID + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }

        Cursor cursor = queryBuilder.query(mDatabaseHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return new String();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
