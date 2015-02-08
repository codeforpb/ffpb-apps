package net.freifunk.paderborn.nodes;

import android.app.*;
import android.content.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;

import net.freifunk.paderborn.nodes.fragments.*;

import org.androidannotations.annotations.*;

/**
 * Activity to show the nodes list
 */
@EActivity(R.layout.activity_nodes)
@OptionsMenu(R.menu.search)
public class Nodes extends ActionBarActivity implements SearchView.OnQueryTextListener {
    @FragmentById
    NodesFragment nodesFragment;

    @SystemService
    SearchManager searchManager;

    @OptionsMenuItem
    MenuItem abSearch;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // explicit class because of auto import ;)
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) abSearch.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @AfterViews
    void checkForSearch() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            nodesFragment.search(query);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        nodesFragment.search(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        nodesFragment.search(s);
        return true;
    }
}
