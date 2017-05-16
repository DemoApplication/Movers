package com.ngagroupinc.movers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/*
import com.google.gson.Gson;
import com.mobile.excelsoft.excelsofttask.DataModel.UserDataModel;
*/

import java.util.regex.Pattern;


/**
 * Created by yakub on 08/10/15.
 */
public class ABUtil {

    public static ABUtil util;
   // private final Typeface boldFont;
    Context context;
    static Typeface lightFont;
    Typeface regularFont;
    Typeface mediumFont;
    private ProgressDialog progressdialog;

    private ABUtil(Context context){
        this.context = context;
//        String lightPath = "fonts/Roboto-Light.ttf";
//        String regularPath = "fonts/Roboto-Regular.ttf";
//        String mediumPath = "fonts/Roboto-Medium.ttf";
//        String boldPath = "fonts/Roboto-Bold.ttf";
//        lightFont = Typeface.createFromAsset(context.getAssets(), lightPath);
//        regularFont = Typeface.createFromAsset(context.getAssets(), regularPath);
//        // mediumFont = Typeface.createFromAsset(context.getAssets(), mediumPath);
//        boldFont = Typeface.createFromAsset(context.getAssets(), boldPath);

    }
    public static ABUtil getInstance(Context context){
        if(util==null)
            util=new ABUtil(context);

        return util;
    }
    //show progress dialog
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
   /* public void loggedIn(UserDataModel user) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        prefsEditor.putString("User", json);
        prefsEditor.commit();
    }

    public UserDataModel isUserLoggedin(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("User", "");
        UserDataModel user = gson.fromJson(json, UserDataModel.class);
        return user;
    }*/

    public boolean isValidEmaillId(String email)
    {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public void loadCities(Boolean load) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putBoolean("loadCities", load);
        prefsEditor.commit();
    }

    public Boolean getLoadCities(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean load = preferences.getBoolean("loadCities", false);
        return load;
    }
/*
    public void getPermission(){}
    */
/*
     * Sets the font on all TextViews in the ViewGroup. Searches
     * recursively for all inner ViewGroups as well. Just add a
     * check for any other views you want to set as well (EditText,
     * etc.)
     *//*

    public void setFont(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for(int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if(v instanceof TextView || v instanceof Button */
/*etc.*//*
)
                ((TextView)v).setTypeface(font);
            else if(v instanceof ViewGroup)
                setFont((ViewGroup)v, font);
        }
    }
    public Typeface getRegularFont(){
        return regularFont;
    }
    public Typeface getBoldFont(){
        return boldFont;
    }
    public Typeface getMediumFont(){
        return mediumFont;
    }
    public Typeface getLightFont(){
        return lightFont;
    }
*/

    public String toUpperCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToUpperCase = Character.toUpperCase(currentChar);
            result = result + currentCharToUpperCase;
        }
        return result;
    }
    //hide keyboard
    public void hideKeyboard(Context ctx) {
        try {
            InputMethodManager inputManager = (InputMethodManager) ctx
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            // check if no view has focus:
            View v = ((Activity) ctx).getCurrentFocus();
            if (v == null)
                return;

            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public String toLowerCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToLowerCase = Character.toLowerCase(currentChar);
            result = result + currentCharToLowerCase;
        }
        return result;
    }

    public String toToggleCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            } else {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            }
        }
        return result;
    }

    public String toCamelCase(String inputString) {
        String result = "";
        if (inputString==null || inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    public static String toSentenceCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        boolean terminalCharacterEncountered = false;
        char[] terminalCharacters = {'.', '?', '!'};
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (terminalCharacterEncountered) {
                if (currentChar == ' ') {
                    result = result + currentChar;
                } else {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                    terminalCharacterEncountered = false;
                }
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
            for (int j = 0; j < terminalCharacters.length; j++) {
                if (currentChar == terminalCharacters[j]) {
                    terminalCharacterEncountered = true;
                    break;
                }
            }
        }
        return result;
    }
    public boolean isConnectingToInternet(){
/*        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }.

        }
        return false;*/

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }




    public static void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView)v).setTypeface(lightFont);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }



    public String getIMEI(){
       /* TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
       return telephonyManager.getDeviceId();*/
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);//get mobile unique id
    }

    public String getVersion(){
//        PackageInfo pInfo = null;
        String version="10"; // Changed to Version 10 for after 2.1 release.
        /*try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
        return version;
    }

    public String getParseDeviceToken(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("parseDeviceToken","");
    }
    public void saveParseDeviceToken(String parseDeviceToken){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("parseDeviceToken",parseDeviceToken);
        edit.commit();
        Log.d("ABUtil","Device token saved");
    }

    public void setFirstTimeInstallation(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("installed", true);
        edit.commit();
    }
    public boolean getIsFirstTimeInstallation(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getBoolean("installed", false);
    }

    public Typeface getRobotoEdittextFont(){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        return font;
    }

    public Typeface getRobotoButtontextFont(){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        return font;
    }

   /* public SpannableString getRobotoLightFont(String conversionString){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        SpannableString mNewTitle = new SpannableString(conversionString);
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return mNewTitle;
    }
    public SpannableString getRobotoBlackFont(String conversionString){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Black.ttf");
        SpannableString mNewTitle = new SpannableString(conversionString);
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return mNewTitle;
    }

    public SpannableString getTabTitleFont(String conversionString){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        SpannableString mNewTitle = new SpannableString(conversionString);
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return mNewTitle;
    }

    public SpannableString getToolbarTitleFont(String conversionString){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(conversionString);
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return mNewTitle;
    }*/




    public boolean signOut(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove("User").commit();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString("User",null);
        if (value == null) {
            return true; // user signed out
        } else {
            return false; // user not signed out
        }
    }

/*

    public User isUserLoggedin(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("User", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public void loggedIn(User user) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        prefsEditor.putString("User", json);
        prefsEditor.commit();
    }

*/



    public String getMobile(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
/*
        Gson gson = new Gson();
*/
        String mobile = preferences.getString("Mobile", null);
        return mobile;
    }

    public void setMobile(String mobile) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString("Mobile", mobile);
        prefsEditor.commit();
    }
}
