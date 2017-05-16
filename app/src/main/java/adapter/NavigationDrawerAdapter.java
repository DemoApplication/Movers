/*
package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ngagroupinc.movers.FragmentDrawer;
import com.ngagroupinc.movers.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.NavDrawerItem;

import static com.ngagroupinc.movers.R.color.nav_selected_items;

*/
/**
 * Created by Ravi Tamada on 12-03-2015.
 *//*

public class NavigationDrawerAdapter extends BaseAdapter {
   ArrayList<String> data_items ;
    ArrayList<Integer> data_icons;
    ArrayList<String>  selections;
    DrawerLayout mDrawerLayout;
    private static int[] selected_icons={R.drawable.account_s,R.drawable.notification_s,R.drawable.about_s,R.drawable.help_s,R.drawable.share_s};

    private LayoutInflater inflater;
    private Context context;
    public NavigationDrawerAdapter(Context context, ArrayList<String> dataitems,ArrayList<Integer> dataicons,DrawerLayout mDrawerLayout) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data_items = dataitems;
        this.data_icons =  dataicons;
        this.mDrawerLayout=mDrawerLayout;
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
     */
/*   holder.nav_item_pic.setImageResource(data_icons.get(position));*//*

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
        */
/*Set a MIME type for the content you're sharing. This will determine which applications the chooser list presents to your users.
         Plain text, HTML, images and videos are among the common types to share. The following Java code demonstrates sending plain text:*//*

        sharing_intent.setType("text/plain");
        */
/*You can pass various elements of your sharing content to the send Intent,
         including subject, text / media content, and addresses to copy to in the case of email sharing.
          This Java code builds a string variable to hold the body of the text content to share:*//*

        holder.nav_item_title = (TextView)view.findViewById(R.id.nav_item_title);

        String shared_body= holder.nav_item_title.getText().toString().trim();
        */
/*Step 9: Pass Content to the Intent*//*

        */
/*Pass your sharing content to the "putExtra" method of the Intent class using the following Java code*//*

        sharing_intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharing_intent.putExtra(android.content.Intent.EXTRA_TEXT, shared_body);
            */
/*Step 10: Create a Chooser*//*

        */
/*Now that you have defined the content to share when the user presses the share button,
         you simply have to instruct Android to let the user choose their sharing medium. Add the following code inside your share method:
This code passes the name of the sharing Intent along with a title to display at the top of the chooser list. This example
uses "Share via" which is a standard option you may have seen in existing apps. However, you can choose a title to suit your own application.*//*

        context.startActivity(Intent.createChooser(sharing_intent, "Share via"));

    }

    static   class Holder
    {
        TextView nav_item_title;
        ImageView  nav_item_pic;


    }
}
*/
