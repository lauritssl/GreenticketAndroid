package dk.greenticket.greenticket;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dk.greenticket.GTmodels.GTConnect;
import dk.greenticket.GTmodels.GTDatabase;
import dk.greenticket.GTmodels.GTUser;

public class MainActivity extends Activity{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences app_preferences = getSharedPreferences("User", MODE_PRIVATE);
        String userEmail = app_preferences.getString("email","N/A");

        if(!userEmail.equalsIgnoreCase("N/A")){
            String userFirstname = app_preferences.getString("firstname","N/A");
            String userLastname = app_preferences.getString("lastname","N/A");
            GTUser user = new GTUser(userEmail, userFirstname, userLastname, this);
            GTApplication application = (GTApplication) getApplication();
            application.setUser(user);

            Intent intent = new Intent(this, LoggedinActivity.class);
            startActivity(intent);
        }

        GTDatabase db = new GTDatabase(getApplicationContext());
        db.clear();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker tracker = EasyTracker.getInstance(this);
        tracker.set(Fields.SCREEN_NAME, "Login");
        tracker.send(MapBuilder.createAppView().build());
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    public void GTLoginClick(View view){
        RelativeLayout loginEmail = (RelativeLayout) findViewById(R.id.LoginEmail);
        loginEmail.setVisibility(View.VISIBLE);
        RelativeLayout loginPassword = (RelativeLayout) findViewById(R.id.LoginPassword);
        loginPassword.setVisibility(View.VISIBLE);
        RelativeLayout loginBack = (RelativeLayout) findViewById(R.id.LoginBack);
        loginBack.setVisibility(View.VISIBLE);
        RelativeLayout loginButton = (RelativeLayout) findViewById(R.id.LoginButton);
        loginButton.setVisibility(View.VISIBLE);
        TextView forgotPassword = (TextView) findViewById(R.id.forgotpassword);
        forgotPassword.setVisibility(View.VISIBLE);

        RelativeLayout FBLoginButton = (RelativeLayout) findViewById(R.id.FBLoginButton);
        FBLoginButton.setVisibility(View.INVISIBLE);
        RelativeLayout GTLoginButton = (RelativeLayout) findViewById(R.id.GTLoginButton);
        GTLoginButton.setVisibility(View.INVISIBLE);



    }

    public void LoginBackClick(View view){
        RelativeLayout loginEmail = (RelativeLayout) findViewById(R.id.LoginEmail);
        loginEmail.setVisibility(View.INVISIBLE);
        RelativeLayout loginPassword = (RelativeLayout) findViewById(R.id.LoginPassword);
        loginPassword.setVisibility(View.INVISIBLE);
        RelativeLayout loginBack = (RelativeLayout) findViewById(R.id.LoginBack);
        loginBack.setVisibility(View.INVISIBLE);
        RelativeLayout loginButton = (RelativeLayout) findViewById(R.id.LoginButton);
        loginButton.setVisibility(View.INVISIBLE);
        TextView forgotPassword = (TextView) findViewById(R.id.forgotpassword);
        forgotPassword.setVisibility(View.INVISIBLE);

        RelativeLayout FBLoginButton = (RelativeLayout) findViewById(R.id.FBLoginButton);
        FBLoginButton.setVisibility(View.VISIBLE);
        RelativeLayout GTLoginButton = (RelativeLayout) findViewById(R.id.GTLoginButton);
        GTLoginButton.setVisibility(View.VISIBLE);

        RelativeLayout resetPasswordButton = (RelativeLayout) findViewById(R.id.forgotPasswordSend);
        resetPasswordButton.setVisibility(View.INVISIBLE);



    }
    public void ForgotPassword(View view){
        RelativeLayout forgotPasswordSend = (RelativeLayout) findViewById(R.id.forgotPasswordSend);
        forgotPasswordSend.setVisibility(View.VISIBLE);
        RelativeLayout loginPassword = (RelativeLayout) findViewById(R.id.LoginPassword);
        loginPassword.setVisibility(View.INVISIBLE);
        RelativeLayout loginButton = (RelativeLayout) findViewById(R.id.LoginButton);
        loginButton.setVisibility(View.INVISIBLE);
        TextView forgotPassword = (TextView) findViewById(R.id.forgotpassword);
        forgotPassword.setVisibility(View.INVISIBLE);

        RelativeLayout resetPasswordButton = (RelativeLayout) findViewById(R.id.forgotPasswordSend);
        resetPasswordButton.setVisibility(View.VISIBLE);



    }


    public void FBLoginClick(View view){
        GTApplication application = (GTApplication) getApplication();
        if(application.isNetworkAvailable()){

            // start Facebook Login
            Session.openActiveSession(this, true, new Session.StatusCallback() {

                // callback when session changes state
                @Override
                public void call(Session session, SessionState state, Exception exception) {
                    if (session.isOpened()) {
                       Request.newMeRequest(session, new Request.GraphUserCallback() {
                            // callback after Graph API response with user object
                           @Override
                           public void onCompleted(GraphUser graphUser, Response response) {
                               if (graphUser != null) {
                                    Log.e("graphUser", graphUser.toString());
                                   final GTUser user = new GTUser(new Integer(graphUser.asMap().get("id").toString()), graphUser.asMap().get("email").toString(), getApplicationContext());
                                   new Thread(new Runnable(){
                                       public void run(){
                                           user.logUserIn();
                                           userLoginChange(user);
                                       }
                                   }).start();

                               }
                           }
                       }).executeAsync();
                    }
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
        }
   }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }



    public void LogoClick(View view){
        Toast.makeText(getApplicationContext(),getResources().getString(R.string.version), Toast.LENGTH_LONG).show();


    }

    public void GTLoginClickButton(View view){
        GTApplication application = (GTApplication) getApplication();
         if(application.isNetworkAvailable()){
                TextView emailField = (TextView) findViewById(R.id.loginEmailField);
                String email = emailField.getText().toString();
                TextView passwordField = (TextView) findViewById(R.id.loginPasswordField);
                String password = passwordField.getText().toString();

                final GTUser user = new GTUser(email,password, getApplicationContext());
                ((GTApplication) this.getApplication()).setUser(user);
                new Thread(new Runnable(){
                    public void run(){
                        user.logUserIn();
                        userLoginChange(user);
                    }
                }).start();
        }else{
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
        }


    }

    public void ForgotPasswordSend(View view){
        GTApplication application = (GTApplication) getApplication();
        if(application.isNetworkAvailable()){
            TextView emailField = (TextView) findViewById(R.id.loginEmailField);
            final String email = emailField.getText().toString();
            new Thread(new Runnable(){
                public void run(){
                    GTConnect con = new GTConnect("users/"+email+"/forgot");
                    JSONObject result = con.POST(null);
                    try{
                        if (result.getBoolean("success")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.forgotpasswordSent), Toast.LENGTH_LONG).show();
                                }
                            });
                        }else{
                            if(result.getString("message").equalsIgnoreCase("User Does not exist")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.forgotpasswordErrorUser), Toast.LENGTH_LONG).show();
                                    }
                                });

                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.login_fail), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }).start();


        }else{
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
        }


    }

    public void userLoginChange(GTUser user){
        if(user.isLoggedIn()){
            GTApplication application = (GTApplication) getApplication();
            application.setUser(user);
            SharedPreferences app_preferences = getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("email", user.getEmail());
            editor.putString("firstname", user.getFirstname());
            editor.putString("lastname", user.getLastname());
            editor.commit();
            Intent intent = new Intent(this, LoggedinActivity.class);
            startActivity(intent);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.login_fail), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
