package dk.greenticket.greenticket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.facebook.Session;

import dk.greenticket.GTmodels.GTDatabase;
import dk.greenticket.GTmodels.GTUser;

public class SettingsFragment extends Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_settings, container, false);

        GTApplication application = (GTApplication) getActivity().getApplication();
        final GTUser user = application.getUser();

        TextView settingsNameField = (TextView) V.findViewById(R.id.settingsNameField);
        TextView settingsMailField = (TextView) V.findViewById(R.id.settingsMailField);

        settingsNameField.setText(user.getFirstname() + " " + user.getLastname());
        settingsMailField.setText(user.getEmail());

        RelativeLayout settingsLogout = (RelativeLayout) V.findViewById(R.id.settingsLogout);
        settingsLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Log.e("shared", "LOG UD");
                SharedPreferences app_preferences = this.getApplication().getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = app_preferences.edit();
                editor.clear();
                editor.commit();*/
                GTApplication application = (GTApplication) getActivity().getApplication();
                if(!user.getFbID().isEmpty()){
                    Session.getActiveSession().close();
                }
                application.setUser(null);
                GTDatabase db = new GTDatabase(getActivity());
                db.clear();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });

        TextView settingsPrivacy = (TextView) V.findViewById(R.id.settingsPrivacy);
        settingsPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("link", "termsOfService");
                startActivity(intent);

            }
        });

        TextView settingsTermsEvent = (TextView) V.findViewById(R.id.settingsTermsEvent);
        settingsTermsEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("link", "termsOfEvents");
                startActivity(intent);


            }
        });

        TextView settingsTermsService = (TextView) V.findViewById(R.id.settingsTermsService);
        settingsTermsService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("link", "privacy");
                startActivity(intent);

            }
        });

        return V;
    }

}
