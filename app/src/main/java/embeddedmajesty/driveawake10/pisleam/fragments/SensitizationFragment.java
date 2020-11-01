package embeddedmajesty.driveawake10.pisleam.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import embeddedmajesty.driveawake10.pisleam.R;
import embeddedmajesty.driveawake10.pisleam.adapter.AwarenessAdapter;
import embeddedmajesty.driveawake10.pisleam.model.AwarenessItem;

public class SensitizationFragment extends Fragment {
    private RecyclerView recyclerAwareness;
    private AwarenessAdapter mAdapter;
    private List<AwarenessItem> items = new ArrayList<AwarenessItem>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.sensitization_fragment, container, false);

        recyclerAwareness = rootView.findViewById(R.id.recyclerView);
        recyclerAwareness.setLayoutManager(new LinearLayoutManager(getActivity()));
        items.add(new AwarenessItem(0,R.drawable.drinkincar,"You should never drink and then drive your car this causes you a huge danger and it may leads to car accident"));
        items.add(new AwarenessItem(0,R.drawable.ic_facialreco,"You should not drive when you are tired but our system has a facial recognition system that detects drowsiness and tiredness and it will alert you if you are not in good condition to drive"));
        items.add(new AwarenessItem(0,R.drawable.ic_temp,"In case of detection of Hyper or Hypo-thermia the alert system of Drive-Awake will be active and it will call an ambulance in case of emergency"));
        items.add(new AwarenessItem(0,R.drawable.wine,"Alcohol affects the entire body, including the brain, nervous system, liver, heart, and the individual's emotional well-being. Its effects are directly related to the amount of alcohol ingested. Factors that influence the effects of alcohol on the body include age, gender, family history of alcoholism, and how much and how often alcohol is consumed."));
        items.add(new AwarenessItem(0,R.drawable.logoblack,"DriveAwake"));
        items.add(new AwarenessItem(0,R.drawable.drowzy,"Drowzy"));
        items.add(new AwarenessItem(0,R.drawable.logoblack,"DriveAwake"));
        items.add(new AwarenessItem(0,R.drawable.logoblack,"DriveAwake"));
        items.add(new AwarenessItem(0,R.drawable.logoblack,"DriveAwake"));





        mAdapter = new AwarenessAdapter(getActivity(),items);

        recyclerAwareness.setAdapter(mAdapter);

        return rootView;
    }
}
