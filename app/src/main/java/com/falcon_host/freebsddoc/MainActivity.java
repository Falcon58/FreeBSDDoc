package com.falcon_host.freebsddoc;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private String[] DocTitles;
    private DrawerLayout dLayout;
    private ListView dList;
    private ActionBarDrawerToggle dToggle;
    private CharSequence dTitle, mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DocTitles = getResources().getStringArray(R.array.doc_array);
        dLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        dList = (ListView)findViewById(R.id.left_drawer);
        mTitle = dTitle = getTitle();

        dLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        dList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, DocTitles));
        dList.setOnItemClickListener(new DrawerItemClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        dToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                dLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(dTitle);
            }
        };
        dLayout.setDrawerListener(dToggle);
        if (savedInstanceState == null) {
            //selectItem(0);
        }
    }
    //@Override
    //public void setTitle(CharSequence title) {
    //    mTitle = title;
    //    getActionBar().setTitle(mTitle);
    //}
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
