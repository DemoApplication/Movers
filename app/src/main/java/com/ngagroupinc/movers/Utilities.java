package com.ngagroupinc.movers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by user1 on 20-Sep-16.
 */
public class Utilities {
    static String  profile_image,first_name,last_name,name;
    public static Utilities util;
    Context context;
    private ProgressDialog progressdialog;
    private AlertDialog alertDialog;

    private Utilities(Context context){
        this.context = context;
    }
    public static Utilities getInstance(Context context){
        if(util==null)
            util=new Utilities(context);

        return util;
    }
    public void showProgressDialog(Activity act, String msg) {
        Log.d("Progress","showProgressDialog");
        if(progressdialog!=null)
            if(progressdialog.isShowing())
                progressdialog.dismiss();
        progressdialog = new ProgressDialog(act);
        progressdialog.setMessage(msg);
        /* progressdialog.setContentView(R.layout.pop_loadprogress);
        AnimatedGifImageView pGif = (AnimatedGifImageView) progressdialog.findViewById(R.id.viewGif);
        pGif.setAnimatedGif(R.raw.anim_loading, AnimatedGifImageView.TYPE.FIT_CENTER);*/
        progressdialog.setCancelable(false);
        progressdialog.setProgress(0);
        progressdialog.setProgressStyle(R.style.AppTheme);
        progressdialog.setIndeterminate(true);
        progressdialog.show();
    }
    //dismiss progress dialog
    public void dismissProgressDialog() {
        Log.d("Progress", "dismissProgressDialog");
        if(progressdialog!=null)
            if(progressdialog.isShowing())
                progressdialog.dismiss();
    }
    /*private void popAlert(Activity act,String msg) {
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Android Alert Message");
        alertDialog.setMessage("Put your description text here!");
        LinearLayout diagLayout = new LinearLayout(this);
        diagLayout.setOrientation(LinearLayout.VERTICAL);
        final TextView text = new TextView(this);
        text.setText("Another text view");
        text.setPadding(10, 10, 10, 10);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(20);
        diagLayout.addView(text);
        alertDialog.setView(diagLayout);
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),
                                "OK Button pressed!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),
                                "CANCEL button pressed!!", Toast.LENGTH_LONG)
                                .show();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }*/



}
