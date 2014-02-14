package dk.greenticket.greenticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import dk.greenticket.GTmodels.GTUser;

public class MainActivity extends Activity{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        RelativeLayout FBLoginButton = (RelativeLayout) findViewById(R.id.FBLoginButton);
        FBLoginButton.setVisibility(View.VISIBLE);
        RelativeLayout GTLoginButton = (RelativeLayout) findViewById(R.id.GTLoginButton);
        GTLoginButton.setVisibility(View.VISIBLE);



    }


    public void FBLoginClick(View view){
        Toast.makeText(getApplicationContext(),"FBclick", Toast.LENGTH_SHORT).show();


    }

    public void LogoClick(View view){
        userLogIn();


    }

    public void GTLoginClickButton(View view){
        TextView emailField = (TextView) findViewById(R.id.loginEmailField);
        String email = emailField.getText().toString();
        TextView passwordField = (TextView) findViewById(R.id.loginPasswordField);
        String password = passwordField.getText().toString();

        final GTUser user = new GTUser(email,password);
        ((GTApplication) this.getApplication()).setUser(user);
        new Thread(new Runnable(){
            public void run(){
                user.logUserIn();
                userLoginChange(user);
            }
        }).start();


    }


    public void userLogIn(){
        /*TextView emailField = (TextView) findViewById(R.id.loginEmailField);
        String email = emailField.getText().toString();
        TextView passwordField = (TextView) findViewById(R.id.loginPasswordField);
        String password = passwordField.getText().toString();*/

        //final GTUser user = new GTUser(email,password);
        //final GTUser user = new GTUser("lau_rits@hotmail.com","123lsl");
        final GTUser user = new GTUser("spjerx@gmail.com","deltaforce");
        ((GTApplication) this.getApplication()).setUser(user);
        new Thread(new Runnable(){
            public void run(){
                user.logUserIn();
                userLoginChange(user);
            }
        }).start();


    }


    public void userLoginChange(GTUser user){
        if(user.isLoggedIn()){
            GTApplication application = (GTApplication) getApplication();
            application.setUser(user);
            Intent intent = new Intent(this, LoggedinActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Fejl, prøv igen..", Toast.LENGTH_LONG).show();
        }
    }

}
