package com.ngagroupinc.movers;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout llv_signup_fb;
    Context mcontext = HomeActivity.this;
    CallbackManager callbackManager;
    Profile u_profile;
    Boolean ishout;
    String url;
    TextView tv_signin;
    String strUserName, name, email, link, pic, data;
    SpannableString spannableText;
    AlertDialog alertDialog;
    private final static String NEAGATIVE = "DENY";
    private final static String POSITIVE = "ALLOW";


    boolean networkEnabled = false;
    boolean GPSEnabled = false;
    boolean is_allow=false;
    String coming_from;
    public static  final String MY_PREFERENCES="Movers";
    LocationManager locationManager;
    private LocationListener locListener = new MyLocationListener();

    Double currentLatitude = null, currentLongitude = null, previous_lattitude, previous_longitude;
    static String j_address1 = null, j_address2 = null, j_state = null, j_city = null, j_country = null, j_pin = null;
    String d_time, mLastUpdateTime;
    List<Address> addresses;
    SharedPreferences preferences;


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 5; // 1 minute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

                /* facebook intialization */
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        printHashKey();
        setContentView(R.layout.activity_home);
                    /* intialization*/
        init();
                 /* here we are writing the text  for spannable text view */
        spannableSignin();

                  /* getting the data from fb*/

    }

    private void spannableSignin() {

        /*************** spannable string *************************
         *
         *how to change the text half in one color and another half in another color and clickable*/
        spannableText = new SpannableString(getResources().getString(R.string.home_tv_already));
        spannableText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "Click!", Toast.LENGTH_LONG).show();

                tv_signin.setBackgroundColor(getResources().getColor(R.color.home_background));

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.sky_blue));
            }
        }, 24, spannableText.length() - 0, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        // clickable text

        tv_signin.setMovementMethod(LinkMovementMethod.getInstance());
        tv_signin.setText(spannableText);

    }

    private void init() {
        llv_signup_fb = (LinearLayout) findViewById(R.id.llv_signup_fb);
        llv_signup_fb.setOnClickListener(this);
        tv_signin = (TextView) findViewById(R.id.tv_already);
        callbackManager = CallbackManager.Factory.create();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    }

    private void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        String text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") + "<br><br><b>Profile link :</b> " + json.getString("link");
                        Log.d("text", text);
                        strUserName = json.getString("name");
                        Log.d("strUserName", strUserName);

                        email = json.getString("email");
                        Log.d("email", email);

                        link = json.getString("link");

                        Log.d("link", link);
                        JSONObject pic = json.getJSONObject("picture");
                        JSONObject data = pic.getJSONObject("data");
                        ishout = data.getBoolean("is_silhouette");

                        Log.d("ishout", " " + ishout);
                        url = data.getString("url");
                        Log.d("url", url);
                        Log.d("response", json.toString());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                u_profile = Profile.getCurrentProfile();
                if (u_profile != null) {
                    Utilities.first_name = u_profile.getFirstName();
                    Log.d("UserName", Utilities.first_name);

                    Utilities.last_name = u_profile.getLastName();
                    Log.d("lastname", Utilities.first_name);

                    Utilities.name = u_profile.getName();
                    Log.d("name", Utilities.name);

                    Utilities.profile_image = u_profile.getProfilePictureUri(100, 150).toString();
                    Log.d("profile_image", Utilities.profile_image);
                    String LOCATIONS= getResources().getString(R.string.allow_locations);
                    popLocationAlert(LOCATIONS, POSITIVE, NEAGATIVE);

                } else

                    Toast.makeText(getApplicationContext(), "can't get the data ,please Login again", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.llv_signup_fb) {
            if (isNetworkAvailable()) {
                FacebookSdk.sdkInitialize(getApplicationContext());
                AppEventsLogger.activateApp(this);
                printHashKey();
                Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_SHORT).show();

                facebook();
            } else
                Toast.makeText(getApplicationContext(), "no internet available", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.dg_tv_submit)
        {
            is_allow=true;
            coming_from="Quotes";
            saveLocation_And_NotificationStatus(is_allow,coming_from);
            Toast.makeText(getApplicationContext(), "Congrats Login Successfull and your status saved", Toast.LENGTH_SHORT).show();

            Intent in = new Intent(HomeActivity.this, FacebookReg.class);
            startActivity(in);
        }else if (id == R.id.dg_tv_cancel){
            is_allow=false;
            coming_from="Quotes";
            saveLocation_And_NotificationStatus(is_allow,coming_from);
            Toast.makeText(getApplicationContext(), "status recieved", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(HomeActivity.this, FacebookReg.class);
            startActivity(in);
        }
    }


    public void printHashKey() {
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
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) mcontext
                .getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "***Available***");
            return true;
        }
        Log.e("Network Testing", "***Not Available***");
        return false;
    }

    private void facebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email", "user_about_me"));
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


    }
                   /* popup to access the location or not  */

    public void popLocationAlert(String msg, String positive_msg, String negative_msg) {
        coming_from="Location";
        final String NOTIFICATIONS = getResources().getString(R.string.allow_notifications);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(positive_msg, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                         getLocation();
                          is_allow=true;
                        saveLocation_And_NotificationStatus(is_allow,coming_from);
                        popNotificationsAlert(NOTIFICATIONS,POSITIVE,NEAGATIVE);
                    }
                })
                .setNegativeButton(negative_msg, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        turnGPSOff();
                        is_allow=false;
                        saveLocation_And_NotificationStatus(is_allow,coming_from);
                       /* Intent in = new Intent(HomeActivity.this, FacebookReg.class);
                        startActivity(in);*/
                        dialog.cancel();
                        popNotificationsAlert(NOTIFICATIONS,POSITIVE,NEAGATIVE);


                    }
                });

        android.app.AlertDialog alert = builder.create();
        alert.show();

    }
                /* popup to send notifications or not  */

                public void popNotificationsAlert(String msg, String positive_msg, String negative_msg) {
                    coming_from="Notifications";

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setMessage(msg)
                            .setCancelable(false)
                            .setPositiveButton(positive_msg, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    is_allow=true;

                                    saveLocation_And_NotificationStatus(is_allow,coming_from);
                                    popQuotesAlert();

                                }
                            })
                            .setNegativeButton(negative_msg, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    turnGPSOff();
                                    is_allow=false;
                                    saveLocation_And_NotificationStatus(is_allow,coming_from);
                                    dialog.cancel();
                                    popQuotesAlert();

                                }
                            });

                    android.app.AlertDialog alert = builder.create();
                    alert.show();

                }
    /*pop up to get the quotes or not*/
    public void popQuotesAlert() {
        Dialog quotes_dialog = new Dialog(this);
        quotes_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quotes_dialog.setContentView(R.layout.submit_dialog);
        quotes_dialog.getWindow().setLayout(600,700);
        TextView submit = (TextView)quotes_dialog.findViewById(R.id.dg_tv_submit);
        TextView cancel=(TextView)quotes_dialog.findViewById(R.id.dg_tv_cancel);
        quotes_dialog.show();
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }



    private void saveLocation_And_NotificationStatus(boolean is_allow,String came_from) {
            preferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor location_access_status = preferences.edit();
        if(came_from.equals("Location")) {
            location_access_status.putBoolean("allow_location", is_allow);
        }else
        if(came_from.equals("Notifications")) {
            location_access_status.putBoolean("allow_notification", is_allow);
        }else
        if(came_from.equals("Quotes"))
        {
            location_access_status.putBoolean("allow_quotes", is_allow);

        }
        location_access_status.commit();
        location_access_status.apply();
    }



    private void turnGPSOn() {
        String provider = Settings.Secure.getString(getContentResolver(),Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled


            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


           /*Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", true);

            sendBroadcast(intent);*/
           /* final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);*/
        }
    }

    private void turnGPSOff() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    public void getLocation() {

        try {
            GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        // don't start listeners if no provider is enabled

        if (GPSEnabled) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locListener);
        }
        if (networkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locListener);
        }
        if(!GPSEnabled&&!networkEnabled)
        {
            turnGPSOn();
            if(GPSEnabled) {
                getLocation();
            }

        }
    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                // This needs to stop getting the location data and save the battery power.
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                updateChanged(location);
                locationManager.removeUpdates(locListener);


            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    }

    public void updateChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        Log.e("currentLat", "" + currentLatitude);
        Log.e("Currrentlon", "" + currentLongitude);
        Geocoder geocoder;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses != null) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                Log.e("currentLat", "" + currentLatitude);
                Log.e("Currrentlon", "" + currentLongitude);
                Log.e("currentaddress", "" + address);
                Log.e("caddress", "" + city);
                Log.e("cstate", "" + state);
                Log.e("country", "" + country);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                d_time = dateFormat.format(date);
                long atTime = location.getTime();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));

               /* if ((Utilities.strUserName != null) && (currentLatitude != null) && (currentLongitude != null) && (d_time != null) && (mLastUpdateTime != null) && (address != null) && (city != null) && (state != null) && (country != null))

                {
                    if ((previous_lattitude != null) && (previous_longitude != null) && (previous_lattitude.equals(currentLatitude)) && (previous_longitude.equals(currentLongitude)))
                    {
                        Toast.makeText(context.getApplicationContext(), "you are in same location", Toast.LENGTH_SHORT).show();

                    } else if (((previous_lattitude == null) && (previous_longitude == null)) || ((previous_lattitude != currentLatitude) && ((previous_longitude != currentLongitude))))
                    {
                        Toast.makeText(context.getApplicationContext(), "you changed your location geocoder", Toast.LENGTH_SHORT).show();
*/
                Log.d("previous_lat", "" + previous_lattitude);
                Log.d("previous_long", "" + previous_longitude);
                previous_lattitude = currentLatitude;
                previous_longitude = currentLongitude;
                Log.d("previous_latitude", "" + previous_lattitude);
                Log.d("previous_longitude", "" + previous_longitude);
                Log.d("d_time", "" + d_time);

                Log.d("mLastUpdateTime", "" + mLastUpdateTime);

                }
            else {
                Toast.makeText(getApplicationContext(), "No addresses returned from geocoder", Toast.LENGTH_SHORT).show();
                boolean status = isNetworkAvailable();
                if (status) {
                    getCurrentLocationViaJSON(currentLatitude, currentLongitude);

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                    Date date = new Date();
                    d_time = dateFormat.format(date);
                    long atTime = location.getTime();
                    mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
                    Log.d("timer", "" + mLastUpdateTime);
                    Log.d("date and time:", dateFormat.format(date));
                   /* logindatabaseadapter = new LoginDataBaseAdapter(context.getApplicationContext());
                    logindatabaseadapter = logindatabaseadapter.open();*/
                    /*if ((Utilities.strUserName != null) && (currentLatitude != null) && (currentLongitude != null) && (d_time != null) && (mLastUpdateTime != null) && (j_address1 != null) && (j_city != null) && (j_state != null) && (j_country != null)) {
                        if ((previous_lattitude != null) && (previous_longitude != null) && (previous_lattitude.equals(currentLatitude)) && (previous_longitude.equals(currentLongitude))) {
                            Toast.makeText(context.getApplicationContext(), "you are in same location", Toast.LENGTH_SHORT).show();

                        } else if (((previous_lattitude == null) && (previous_longitude == null)) || ((previous_lattitude != currentLatitude) && ((previous_longitude != currentLongitude)))) {*/
                    previous_lattitude = currentLatitude;
                    previous_longitude = currentLongitude;
                    Log.d("previous_latitude", "" + previous_lattitude);
                    Log.d("previous_longitude", "" + previous_longitude);
                    Log.d("d_time", "" + d_time);

                    Log.d("mLastUpdateTime", "" + mLastUpdateTime);
                   /* logindatabaseadapter.insertMapsEntry(Utilities.strUserName, currentLatitude, currentLongitude, d_time, mLastUpdateTime, j_address1, j_city, j_state, j_country);
                    logindatabaseadapter.close();*/
                    currentLatitude = null;
                    currentLongitude = null;
                    d_time = null;
                    mLastUpdateTime = null;
                    j_address1 = null;
                    j_city = null;
                    j_state = null;
                    j_country = null;

                    Toast.makeText(getApplicationContext(), "its time to work from Json ", Toast.LENGTH_SHORT).show();
                        /*}
                    }*/
                }else
                    Toast.makeText(getApplicationContext(), "you dont have internet connection", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No addresses returned from geocoder", Toast.LENGTH_SHORT).show();

            boolean is_internet=isNetworkAvailable();
            if (is_internet) {
                getCurrentLocationViaJSON(currentLatitude, currentLongitude);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                d_time = dateFormat.format(date);
                long atTime = location.getTime();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
                Log.d("timer", "" + mLastUpdateTime);
                Log.d("date and time:", dateFormat.format(date));

           /* if ((Utilities.strUserName != null) && (currentLatitude != null) && (currentLongitude != null) && (d_time != null) && (mLastUpdateTime != null) && (j_address1 != null) && (j_city != null) && (j_state != null) && (j_country != null)) {
                if ((previous_lattitude != null) && (previous_longitude != null) && (previous_lattitude.equals(currentLatitude)) && (previous_longitude.equals(currentLongitude))) {
                    Toast.makeText(context.getApplicationContext(), "you are in same location", Toast.LENGTH_SHORT).show();

                } else if (((previous_lattitude == null) && (previous_longitude == null)) || ((previous_lattitude != currentLatitude) && ((previous_longitude != currentLongitude))))
                {*/
                previous_lattitude = currentLatitude;
                previous_longitude = currentLongitude;
                Log.d("previous_latitude", "" + previous_lattitude);
                Log.d("previous_longitude", "" + previous_longitude);
               /* logindatabaseadapter = new LoginDataBaseAdapter(context.getApplicationContext());
                logindatabaseadapter = logindatabaseadapter.open();

                logindatabaseadapter.insertMapsEntry(Utilities.strUserName, currentLatitude, currentLongitude, d_time, mLastUpdateTime, j_address1, j_city, j_state, j_country);
                logindatabaseadapter.close();*/
                Log.d("d_time", "" + d_time);

                Log.d("mLastUpdateTime", "" + mLastUpdateTime);

                currentLatitude = null;
                currentLongitude = null;
                d_time = null;
                mLastUpdateTime = null;
                j_address1 = null;
                j_city = null;
                j_state = null;
                j_country = null;

                Toast.makeText(getApplicationContext(), "its time to work from Json ", Toast.LENGTH_SHORT).show();
               /* }
            }*/
            }
        }
    }

    public static JSONObject getLocationInfo(double lat, double lng) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);

            HttpGet httpGet = new HttpGet(
                    "http://maps.googleapis.com/maps/api/geocode/json?latlng="
                            + lat + "," + lng + "&sensor=true");
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            StringBuilder stringBuilder = new StringBuilder();

            try {
                response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                InputStream stream = entity.getContent();
                int b;
                while ((b = stream.read()) != -1) {
                    stringBuilder.append((char) b);
                }
            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(stringBuilder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("response", "" + jsonObject);
            return jsonObject;
        }
        return null;
    }

    public static String getCurrentLocationViaJSON(double lat, double lng) {

        JSONObject jsonObj = getLocationInfo(lat, lng);
        Log.i("JSON string =>", jsonObj.toString());

        String Address1 = "";
        String Address2 = "";
        String City = "";
        String State = "";
        String Country = "";
        String County = "";
        String PIN = "";

        String currentLocation = "";

        try {
            String status = jsonObj.getString("status").toString();
            Log.i("status", status);

            if (status.equalsIgnoreCase("OK")) {
                JSONArray Results = jsonObj.getJSONArray("results");
                JSONObject zero = Results.getJSONObject(0);
                JSONArray address_components = zero
                        .getJSONArray("address_components");

                for (int i = 0; i < address_components.length(); i++) {
                    JSONObject zero2 = address_components.getJSONObject(i);
                    String long_name = zero2.getString("long_name");
                    JSONArray mtypes = zero2.getJSONArray("types");
                    String Type = mtypes.getString(0);

                    if (Type.equalsIgnoreCase("street_number")) {
                        Address1 = long_name + " ";
                    } else if (Type.equalsIgnoreCase("route")) {
                        Address1 = Address1 + long_name;
                    } else if (Type.equalsIgnoreCase("sublocality")) {
                        Address2 = long_name;
                    } else if (Type.equalsIgnoreCase("locality")) {
                        // Address2 = Address2 + long_name + ", ";
                        City = long_name;
                    } else if (Type
                            .equalsIgnoreCase("administrative_area_level_2")) {
                        County = long_name;
                    } else if (Type
                            .equalsIgnoreCase("administrative_area_level_1")) {
                        State = long_name;
                    } else if (Type.equalsIgnoreCase("country")) {
                        Country = long_name;
                    } else if (Type.equalsIgnoreCase("postal_code")) {
                        PIN = long_name;
                    }

                }

                currentLocation = Address1 + "," + Address2 + "," + City + ","
                        + State + "," + Country + "," + PIN;
                j_address1 = Address1;
                j_address2 = Address2;
                j_city = City;
                j_state = State;
                j_country = Country;
                j_pin = PIN;
            }
        } catch (Exception e) {

        }
        return currentLocation;

    }
}
