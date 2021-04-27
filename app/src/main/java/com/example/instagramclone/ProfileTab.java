package com.example.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ProfileTab extends Fragment {


    EditText edtProfileName;
    EditText edtProfileBio;
    EditText edtProfileOccupation;
    EditText edtProfileHobby;
    EditText edtProfileSport;

    Button updateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }


    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileHobby = view.findViewById(R.id.edtProfileHobby);
        edtProfileOccupation = view.findViewById(R.id.edtProfileOccupation);
        edtProfileSport = view.findViewById(R.id.edtProfilSport);
        updateInfo = view.findViewById(R.id.updateProfileButton);

        ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser.get("ProfileName")==null) {
            edtProfileName.setText("");
        } else {
            edtProfileName.setText(parseUser.get("ProfileName")+"");
        }

        if(parseUser.get("ProfileBio")==null) {
            edtProfileBio.setText("");
        } else {
            edtProfileBio.setText(parseUser.get("ProfileBio")+"");
        }

        if(parseUser.get("ProfileHobby")==null) {
            edtProfileHobby.setText("");
        } else {
            edtProfileHobby.setText(parseUser.get("ProfileHobby")+"");
        }

        if(parseUser.get("ProfileOccupation")==null) {
            edtProfileOccupation.setText("");
        } else {
            edtProfileOccupation.setText(parseUser.get("ProfileOccupation")+"");
        }

        if(parseUser.get("ProfileSport")==null) {
            edtProfileSport.setText("");
        } else {
            edtProfileSport.setText(parseUser.get("ProfileSport")+"");
        }


        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseUser.put("ProfileName",edtProfileName.getText().toString());
                parseUser.put("ProfileBio",edtProfileBio.getText().toString());
                parseUser.put("ProfileHobby",edtProfileHobby.getText().toString());
                parseUser.put("ProfileOccupation",edtProfileOccupation.getText().toString());
                parseUser.put("ProfileSport",edtProfileSport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Toast.makeText(getContext(), "Information Updated !!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "error try again !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}