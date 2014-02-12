package dk.greenticket.greenticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    public void GTLogin_btn_click(View view){
        TextView emailField = (TextView) findViewById(R.id.loginEmailField);
        String email = emailField.getText().toString();
        TextView passwordField = (TextView) findViewById(R.id.loginPasswordField);
        String password = passwordField.getText().toString();

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
