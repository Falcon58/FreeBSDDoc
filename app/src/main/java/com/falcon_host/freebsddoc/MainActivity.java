package com.falcon_host.freebsddoc;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

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
                getActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(dTitle);
            }
        };
        dLayout.setDrawerListener(dToggle);
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        //getActionBar().setTitle(mTitle);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new TextFragment();
        Bundle args = new Bundle();
        args.putInt("NUMBER", position);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        dList.setItemChecked(position, true);
        dLayout.closeDrawer(dList);
    }
    public static class TextFragment extends Fragment {

        public TextFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_text, container, false);
            int i = getArguments().getInt("NUMBER");
            String docTitle = getResources().getStringArray(R.array.doc_array)[i];
            Resources r = this.getResources();
            InputStream input;
            switch(i)
            {
                case 0:
                    input = r.openRawResource(R.raw.file_0);
                    break;
                case 1:
                    input = r.openRawResource(R.raw.file_1);
                    break;
                case 2:
                    input = r.openRawResource(R.raw.file_2);
                    break;
                case 3:
                    input = r.openRawResource(R.raw.file_3);
                    break;
                case 4:
                    input = r.openRawResource(R.raw.file_4);
                    break;
                case 5:
                    input = r.openRawResource(R.raw.file_5);
                    break;
                case 6:
                    input = r.openRawResource(R.raw.file_6);
                    break;
                default:
                    input = r.openRawResource(R.raw.file_0);
            }
            try
            {
                String temp = toString(input);
                input.close();
                WebView Web = (WebView)rootView.findViewById(R.id.doc_text);
                Web.loadData(temp, "text/html", "utf-8");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            getActivity().setTitle(docTitle);
            return rootView;
        }
        public String toString(InputStream in) throws IOException
        {
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            int i = in.read();
            while(i != -1)
            {
                temp.write(i);
                i = in.read();
            }
            return temp.toString();
        }
    }
}
