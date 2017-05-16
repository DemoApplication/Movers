package com.ngagroupinc.movers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Context mContext = MainActivity.this;
    CallbackManager callbackManager;
    Profile u_profile;
    RelativeLayout rll_fb;
    Boolean ishout;
    String url;
    String strUserName,name,email,link,pic,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        printHashKey();

        setContentView(R.layout.activity_main);
        init();
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    RequestData();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        rll_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(),"connected",Toast.LENGTH_SHORT).show();

                    facebook();
                }
                else
                    Toast.makeText(getApplicationContext(),"no internet available",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void printHashKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ngagroupinc.movers",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch(PackageManager.NameNotFoundException e) {

        } catch(NoSuchAlgorithmException e) {

        }
    }
public void init()
    {
        rll_fb=(RelativeLayout)findViewById(R.id.rll_fb);
    }





    public boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "***Available***");
            return true;
        }
        Log.e("Network Testing", "***Not Available***");
        return false;
    }
    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                   String     text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") + "<br><br><b>Profile link :</b> " + json.getString("link");
                    Log.d("text",text);
                        strUserName = json.getString("name");
                        Log.d("strUserName",strUserName);

                           email = json.getString("email");
                        Log.d("email",email);

                        link = json.getString("link");

                        Log.d("link",link);
                        JSONObject pic = json.getJSONObject("picture");
                        JSONObject data = pic.getJSONObject("data");
                        ishout = data.getBoolean("is_silhouette");

                        Log.d("ishout", " "+ishout);
                        url = data.getString("url");

                        Log.d("url",url);
                        Log.d("response", json.toString());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    u_profile = Profile.getCurrentProfile();
                   Utilities.first_name = u_profile.getFirstName();
                Log.d("UserName",Utilities.first_name);

              Utilities.last_name = u_profile.getLastName();
                Log.d("lastname",Utilities.first_name);

            Utilities.name=   u_profile.getName();
                Log.d("name",Utilities.name);

                Utilities.profile_image = u_profile.getProfilePictureUri(100,150).toString();
                Log.d("profile_image",Utilities.profile_image);
               Intent in = new Intent(MainActivity.this,FacebookReg.class);
                startActivity(in);

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void facebook()  {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends","email","user_about_me"));


//        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//                if (AccessToken.getCurrentAccessToken() != null) {
//                    RequestData();
//                }
//            }
//
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//            }
//        });


    }



}
