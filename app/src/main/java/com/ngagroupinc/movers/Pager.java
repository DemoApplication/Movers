package com.ngagroupinc.movers;

/**
 * Created by uma on 10/23/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                InventoryTab tab_inventory = new InventoryTab();
                return tab_inventory;
            case 1:
                GuidesTab tab_guides = new GuidesTab();
                return tab_guides;
            case 2:
                CheckListTab tab_checklist = new CheckListTab();
                return tab_checklist;
            case 3:
                CompareTab tab_compare = new CompareTab();
                return tab_compare;
            case 4:
                QuotesTab tab_quotes = new QuotesTab();
                return tab_quotes;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
