package com.ngagroupinc.movers;

/**
 * Created by user1 on 13-Sep-16.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.NavDrawerItem;


public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private ListView list_view;
    private ActionBarDrawerToggle mDrawerToggle;
    private  DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    ArrayList<String> data_items;
    ImageView iv_icon;
    TextView tv_item;
   static  ArrayList<Integer> selected;
    ArrayList<Integer> data_icons;
    private static String[] titles =null;
    private static int[] icons={R.drawable.account,R.drawable.notifications,R.drawable.about,R.drawable.help,R.drawable.share};
    private static int[] selected_icons={R.drawable.account_s,R.drawable.notification_s,R.drawable.about_s,R.drawable.help_s,R.drawable.share_s};

    private FragmentDrawerListener drawerListener;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public ArrayList<String> getDataItems() {

        data_items= new ArrayList<String>();

        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            data_items.add(titles[i]);
        }
        return data_items;
    }
    public ArrayList<Integer> getDataIcons() {

        data_icons= new ArrayList<Integer>();

        // preparing navigation drawer items
        for (int i = 0; i < icons.length; i++) {
            data_icons.add(icons[i]);
        }
        return data_icons;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_titles);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        list_view = (ListView) layout.findViewById(R.id.drawerList);
selected=new ArrayList<Integer>();
        adapter = new NavigationDrawerAdapter(getActivity(), getDataItems(),getDataIcons());
        list_view.setAdapter(adapter);


       list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"fid me",Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(containerView);


            }
        });


        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset/2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),"fid me",Toast.LENGTH_SHORT).show();
  *//*iv_icon=(ImageView)view.findViewById(R.id.nav_item_img);
        tv_item=(TextView)view.findViewById(R.id.nav_item_title);
        String selected_item=tv_item.getText().toString().trim();
        if(selected_item.equals("Account"))
        {
            iv_icon.setImageResource(R.drawable.account_s);
            tv_item.setTextColor(getResources().getColor(R.color.nav_selected_items));
        }else if(selected_item.equals("Notifications"))
        {
            iv_icon.setImageResource(R.drawable.notification_s);
            tv_item.setTextColor(getResources().getColor(R.color.nav_selected_items));
        }else if(selected_item.equals("About"))
        {
            iv_icon.setImageResource(R.drawable.about_s);
            tv_item.setTextColor(getResources().getColor(R.color.nav_selected_items));
        }else if(selected_item.equals("Help"))
        {
            iv_icon.setImageResource(R.drawable.help_s);
            tv_item.setTextColor(getResources().getColor(R.color.nav_selected_items));
        }else if(selected_item.equals("Share"))
        {
            iv_icon.setImageResource(R.drawable.share_s);
            tv_item.setTextColor(getResources().getColor(R.color.nav_selected_items));
        }
        mDrawerLayout.closeDrawer(containerView);*//*


    }*/

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }


    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);

    }



    public class NavigationDrawerAdapter extends BaseAdapter {
        ArrayList<String> data_items ;
        ArrayList<Integer> data_icons;
        ArrayList<String>  selections;
        private  int[] selected_icons={R.drawable.account_s,R.drawable.notification_s,R.drawable.about_s,R.drawable.help_s,R.drawable.share_s};

        private LayoutInflater inflater;
        private Context context;
        public NavigationDrawerAdapter(Context context, ArrayList<String> dataitems,ArrayList<Integer> dataicons) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.data_items = dataitems;
            this.data_icons =  dataicons;
            this.selections= new ArrayList<String>();
        }


        @Override
        public int getCount() {
            return data_items.size();
        }

        @Override
        public Object getItem(int position) {
            return data_items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            Holder holder = null;

            if(row==null){
                Integer selected_position = -1;

                row=inflater.inflate(R.layout.nav_drawer_row,parent,false);
                holder=new Holder();
                holder.nav_item_pic = (ImageView)row.findViewById(R.id.nav_item_img);
                holder.nav_item_title = (TextView)row.findViewById(R.id.nav_item_title);
                row.setTag(holder);
            } else {

                holder = (Holder)row.getTag();
            }
            try {
                if (selections.get(position).equals("1")) {
                    holder.nav_item_pic.setImageResource(selected_icons[position]);
                    holder.nav_item_title.setTextColor(Color.parseColor("#03a9f4"));
                } else {
                    holder.nav_item_pic.setImageResource(data_icons.get(position));
                    holder.nav_item_title.setTextColor(Color.parseColor("#8D8D8D"));

                }

            }catch (Exception e)
            {
                holder.nav_item_pic.setImageResource(data_icons.get(position));
            }
     /*   holder.nav_item_pic.setImageResource(data_icons.get(position));*/
            holder.nav_item_pic.setMaxHeight(50);
            holder.nav_item_title.setText(data_items.get(position));
            final Holder finalHolder1 = holder;
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selections.clear();
                    for(int i=0;i<data_icons.size();i++)
                    {
                        if(position==i) {
                            selections.add("1");

                        }
                        else
                            selections.add("0");


                    }
                    String selected_text= finalHolder1.nav_item_title.getText().toString().trim();
                    if(selected_text.equals("Share"))
                    {
                        shareit(v);
                    }

                    notifyDataSetChanged();
                    mDrawerLayout.closeDrawers();

                }
            });
            return row;
        }

        private void shareit(View view)
        {
            Holder holder = new Holder();
            Intent sharing_intent = new Intent(Intent.ACTION_SEND);
        /*Set a MIME type for the content you're sharing. This will determine which applications the chooser list presents to your users.
         Plain text, HTML, images and videos are among the common types to share. The following Java code demonstrates sending plain text:*/
            sharing_intent.setType("text/plain");
        /*You can pass various elements of your sharing content to the send Intent,
         including subject, text / media content, and addresses to copy to in the case of email sharing.
          This Java code builds a string variable to hold the body of the text content to share:*/
            holder.nav_item_title = (TextView)view.findViewById(R.id.nav_item_title);

            String shared_body= holder.nav_item_title.getText().toString().trim();
        /*Step 9: Pass Content to the Intent*/
        /*Pass your sharing content to the "putExtra" method of the Intent class using the following Java code*/
            sharing_intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharing_intent.putExtra(android.content.Intent.EXTRA_TEXT, shared_body);
            /*Step 10: Create a Chooser*/
        /*Now that you have defined the content to share when the user presses the share button,
         you simply have to instruct Android to let the user choose their sharing medium. Add the following code inside your share method:
This code passes the name of the sharing Intent along with a title to display at the top of the chooser list. This example
uses "Share via" which is a standard option you may have seen in existing apps. However, you can choose a title to suit your own application.*/
            context.startActivity(Intent.createChooser(sharing_intent, "Share via"));

        }


    }
    class Holder
    {
        TextView nav_item_title;
        ImageView  nav_item_pic;


    }

}
