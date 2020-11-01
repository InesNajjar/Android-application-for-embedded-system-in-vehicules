package embeddedmajesty.driveawake10.pisleam.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import embeddedmajesty.driveawake10.pisleam.R;

public class SettingsFragment extends  Fragment {
    private Button save;



    private EditText editText;


    private static final String TAG = "Settings";

    private static final String SHARED_PREFS ="sharedPrefs";
    private static final String TEXT ="text";
    private String loadtext;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview;
        rootview = inflater.inflate(R.layout.settings_fragment, container, false);

        save = rootview.findViewById(R.id.saveb);
        editText = rootview.findViewById(R.id.familynumber);


        save.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                editText.setText(editText.getText().toString());
                saveData();
            }
        });



        loadData();
        update();

        return rootview;
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString(TEXT,editText.getText().toString());
        editor.apply();
        Toast.makeText(getActivity(),"data saved",Toast.LENGTH_SHORT).show();
    }
    public void loadData()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        loadtext =sharedPreferences.getString(TEXT,"");
    }

    public void update()
    {
        editText.setText(loadtext);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}



