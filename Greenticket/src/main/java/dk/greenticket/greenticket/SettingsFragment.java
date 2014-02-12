package dk.greenticket.greenticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import dk.greenticket.GTmodels.GTUser;

public class SettingsFragment extends Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_settings, container, false);

        GTApplication application = (GTApplication) getActivity().getApplication();
        GTUser user = application.getUser();

        TextView settingsNameField = (TextView) V.findViewById(R.id.settingsNameField);
        TextView settingsMailField = (TextView) V.findViewById(R.id.settingsMailField);

        settingsNameField.setText(user.getFirstname() + " " + user.getLastname());
        settingsMailField.setText(user.getEmail());

        TextView settingsLogout = (TextView) V.findViewById(R.id.settingsLogOut);
        settingsLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GTApplication application = (GTApplication) getActivity().getApplication();
                application.setUser(null);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return V;
    }



}
