package net.freifunk.paderborn.nodes.sync;

import android.content.*;
import android.database.*;
import android.net.*;

import org.androidannotations.annotations.*;

/**
 * A content provider for the Sync Adapter. Right now most things are stubbed.
 *
 * Path structure: (authority: net.freifunk.paderborn)
 * /nodes - list of nodes
 * /nodes/(#|*) - single node via (id|remote id)
 * /nodes/(#|*)/field - value of field on single node identified via (id|remote id) [only important!]
 * /links - list of links
 * /links/(#|*) - single link via (id|remote id)
 *
 */
@EProvider
public class FfpbNodesContentProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
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
