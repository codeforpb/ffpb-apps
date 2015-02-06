package net.freifunk.paderborn.nodes.fragments;

import android.content.*;
import android.widget.*;

import net.freifunk.paderborn.nodes.api.*;

import java.util.*;

/**
 * Created by ljan on 06.02.15.
 */
public class TemporaryAdapter extends ArrayAdapter<Node> {
    public TemporaryAdapter(Context context, List<Node> items) {
        super(context, android.R.layout.simple_list_item_1, items);
    }

}
