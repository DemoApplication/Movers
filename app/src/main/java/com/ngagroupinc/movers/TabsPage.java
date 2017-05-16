package com.ngagroupinc.movers;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user1 on 20-Oct-16.
 */

public class TabsPage extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener,TabLayout.OnTabSelectedListener {

    private static String TAG = TabsPage.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    //This is our tablayout
    private TabLayout tabLayout;
    ImageView icon_inventory,icon_guides,icon_checklist,icon_compare,icon_quotes;
    TextView text_inventory,text_guides,text_checklist,text_compare,text_quotes;

    //This is our viewPager
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout),mToolbar);
        drawerFragment.setDrawerListener(this);
        //Initializing the tablayout

        tabLayout=(TabLayout)findViewById(R.id.tabs);

        //Adding the tabs using addTab() method

                           /*Inventory view*/
        View inventory_view = (View)getLayoutInflater().inflate(R.layout.custom_tab,null);
        icon_inventory=(ImageView)inventory_view.findViewById(R.id.tab_icon);
        text_inventory=(TextView)inventory_view.findViewById(R.id.tab_text);
        icon_inventory.setImageResource(R.drawable.inventory);
        text_inventory.setText(R.string.tab_inventory);
                       /*guides view*/
        View guides_view = (View)getLayoutInflater().inflate(R.layout.custom_tab,null);
        icon_guides=(ImageView)guides_view.findViewById(R.id.tab_icon);
        text_guides=(TextView)guides_view.findViewById(R.id.tab_text);
        icon_guides.setImageResource(R.drawable.guides);
        text_guides.setText(R.string.tab_guides);
                       /*checklist*/
        View checklist_view = (View)getLayoutInflater().inflate(R.layout.custom_tab,null);
        icon_checklist=(ImageView)checklist_view.findViewById(R.id.tab_icon);
        text_checklist=(TextView)checklist_view.findViewById(R.id.tab_text);
        icon_checklist.setImageResource(R.drawable.checklist);
        text_checklist.setText(R.string.tab_checklist);
                       /*compare*/
        View compare_view = (View)getLayoutInflater().inflate(R.layout.custom_tab,null);
        icon_compare=(ImageView)compare_view.findViewById(R.id.tab_icon);
        text_compare=(TextView)compare_view.findViewById(R.id.tab_text);
        icon_compare.setImageResource(R.drawable.compare);
        text_compare.setText(R.string.tab_compare);
                       /*quotes view*/
        View quotes_view = (View)getLayoutInflater().inflate(R.layout.custom_tab,null);
        icon_quotes=(ImageView)quotes_view.findViewById(R.id.tab_icon);
        text_quotes=(TextView)quotes_view.findViewById(R.id.tab_text);
        icon_quotes.setImageResource(R.drawable.quotes);
        text_quotes.setText(R.string.tab_quotes);

        tabLayout.addTab(tabLayout.newTab().setCustomView(inventory_view));
        tabLayout.addTab(tabLayout.newTab().setCustomView(guides_view));
        tabLayout.addTab(tabLayout.newTab().setCustomView(checklist_view));
        tabLayout.addTab(tabLayout.newTab().setCustomView(compare_view));
        tabLayout.addTab(tabLayout.newTab().setCustomView(quotes_view));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.btn_text_color));
/*
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
*/
        //Initializing the view Pager
        viewPager=(ViewPager)findViewById(R.id.pager);
        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        // display the first navigation drawer view on app launch
        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        displayView(0);
    }



    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new AccountFragment();
                title = getString(R.string.nav_item_account);
                break;
            case 1:
                fragment = new NotificationsFragment();
                title = getString(R.string.nav_item_notification);
                break;
            case 2:
                Toast.makeText(getApplicationContext(),"About will be displayed in web view",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getApplicationContext(),"Help will be displayed in web view",Toast.LENGTH_SHORT).show();

                break;
            case 4:
                Toast.makeText(getApplicationContext(),"share via will be displayed in web view",Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }

       /* if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }*/
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position=tab.getPosition();
        viewPager.setCurrentItem(position);
        switch (position) {
            case 0:
                text_inventory.setVisibility(View.VISIBLE);
                break;
            case 1:
                text_guides.setVisibility(View.VISIBLE);
                 break;
            case 2:
                text_checklist.setVisibility(View.VISIBLE);
                 break;
            case 3:
                text_compare.setVisibility(View.VISIBLE);
                break;
            case 4:
                text_quotes.setVisibility(View.VISIBLE);
                  break;
            default:
                 break;
        }

       /* tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.inventory).setText(R.string.tab_inventory));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.guides).setText(R.string.tab_guides));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.checklist).setText(R.string.tab_checklist));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.compare).setText(R.string.tab_compare));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.quotes).setText(R.string.tab_quotes));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);*/
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int position=tab.getPosition();
        viewPager.setCurrentItem(position);
        switch (position) {
            case 0:
                text_inventory.setVisibility(View.GONE);
                break;
            case 1:
                text_guides.setVisibility(View.GONE);
                break;
            case 2:
                text_checklist.setVisibility(View.GONE);
                break;
            case 3:
                text_compare.setVisibility(View.GONE);
                break;
            case 4:
                text_quotes.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}