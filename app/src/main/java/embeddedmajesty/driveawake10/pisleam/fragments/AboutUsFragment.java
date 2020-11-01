package embeddedmajesty.driveawake10.pisleam.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import embeddedmajesty.driveawake10.pisleam.R;
import embeddedmajesty.driveawake10.pisleam.adapter.SliderAdapter;
import embeddedmajesty.driveawake10.pisleam.model.SliderItem;

public class AboutUsFragment extends Fragment {
    SliderView sliderView;
    private SliderAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview;
        rootview=inflater.inflate(R.layout.aboutus_fragment,container,false);
        sliderView = rootview.findViewById(R.id.imageSlider);
        adapter = new SliderAdapter(getActivity());
        sliderView.setSliderAdapter(adapter);
        renewItems(null);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(5);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        return rootview;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();



        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("When you start the engine in the car the Drive-Awake system starts automatically");
        sliderItem.setImageUrl(R.drawable.startengine);
        sliderItemList.add(sliderItem);

        SliderItem sliderItem2 = new SliderItem();
        sliderItem2.setDescription("You should connect through bluetooth in order to get your health parameters in the mobile app screen");
        sliderItem2.setImageUrl(R.drawable.fleche);
        sliderItemList.add(sliderItem2);

        SliderItem sliderItem3 = new SliderItem();
        sliderItem3.setDescription("Place your hand correctly on the steering wheel where the sensors are placed");
        sliderItem3.setImageUrl(R.drawable.steeringwheel);
        sliderItemList.add(sliderItem3);

        SliderItem s = new SliderItem();
        s.setDescription("Everything is set now drive safe with Drive-Awake");
        s.setImageUrl(R.drawable.ic_logo_blue);
        sliderItemList.add(s);


        /*
        for (int i = 0; i < 10; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl(R.drawable.ic_alcohol);
            } else {
                sliderItem.setImageUrl(R.drawable.ic_heartrate);
            }
            sliderItemList.add(sliderItem);
        }*/
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl(R.drawable.ic_google_logo);
        adapter.addItem(sliderItem);
    }
}
