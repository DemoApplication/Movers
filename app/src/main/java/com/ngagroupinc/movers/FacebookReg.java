package com.ngagroupinc.movers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FacebookReg extends AppCompatActivity {
    EditText pop_first_name;
    EditText pop_last_name;
    EditText pop_mobilenum;
    RoundedImageView iv_pic;
    Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_facebook_reg);
        init();

        if(Utilities.name!=null)
        {
            populateFbDetails();

        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacebookReg.this,MyMovingProfile.class));
            }
        });
    }
    protected void init()
    {
        pop_first_name=(EditText)findViewById(R.id.ed_first_name);
        pop_last_name=(EditText)findViewById(R.id.ed_last_name);
        pop_mobilenum=(EditText)findViewById(R.id.ed_mobile_number);
        iv_pic=(RoundedImageView)findViewById(R.id.iv_pic);
        btn_next=(Button)findViewById(R.id.btn_next);

    }
    protected void populateFbDetails()
    {
        if((Utilities.first_name!=null)&&(Utilities.last_name!=null)&&(Utilities.profile_image!=null))
        {
            pop_first_name.setText(Utilities.first_name);
            pop_last_name.setText(Utilities.last_name);
            if (!TextUtils.isEmpty(Utilities.profile_image.trim())) {
                new FbNumberImage().
                        execute();
            }
        }

    }
    class FbNumberImage extends AsyncTask<Bitmap, Bitmap, Bitmap> {

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            Bitmap bitmap = null;
            try {
                URL url2 = new URL(Utilities.profile_image);
                HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iv_pic.setImageBitmap(bitmap);
        }
    }
}
