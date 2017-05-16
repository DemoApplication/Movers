package com.ngagroupinc.movers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by user1 on 26-Sep-16.
 */

public class SharingActivity  extends AppCompatActivity  {

Button btn_sharing;
    ImageView share;
    EditText ed_passcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_sharing);

        btn_sharing =(Button)findViewById(R.id.btn_sharing_ok);
        share = (ImageView)findViewById(R.id.iv_share);
        ed_passcode=(EditText)findViewById(R.id.ed_passcode);
        btn_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SharingActivity.this,ConfirmRegistration.class));
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();

            }
        });


    }
    private void shareIt()
    {

        Intent  sharing_intent = new Intent(Intent.ACTION_SEND);
        /*Set a MIME type for the content you're sharing. This will determine which applications the chooser list presents to your users.
         Plain text, HTML, images and videos are among the common types to share. The following Java code demonstrates sending plain text:*/
         sharing_intent.setType("text/plain");
        /*You can pass various elements of your sharing content to the send Intent,
         including subject, text / media content, and addresses to copy to in the case of email sharing.
          This Java code builds a string variable to hold the body of the text content to share:*/
        String shared_body= ed_passcode.getText().toString();
        /*Step 9: Pass Content to the Intent*/
        /*Pass your sharing content to the "putExtra" method of the Intent class using the following Java code*/
        sharing_intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharing_intent.putExtra(android.content.Intent.EXTRA_TEXT, shared_body);
            /*Step 10: Create a Chooser*/
        /*Now that you have defined the content to share when the user presses the share button,
         you simply have to instruct Android to let the user choose their sharing medium. Add the following code inside your share method:
This code passes the name of the sharing Intent along with a title to display at the top of the chooser list. This example
uses "Share via" which is a standard option you may have seen in existing apps. However, you can choose a title to suit your own application.*/
        startActivity(Intent.createChooser(sharing_intent, "Share via"));

    }
}
