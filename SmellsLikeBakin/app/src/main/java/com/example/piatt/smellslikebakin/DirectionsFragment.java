package com.example.piatt.smellslikebakin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * Created by piatt on 9/6/2016.
 */
public class DirectionsFragment extends Fragment {

    private static final String KEY_CHECKED_BOXES = "key_ckecked_boxes";
    private CheckBox[] mCheckBoxes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int index = getArguments().getInt(ViewPagerFragment.KEY_RECIPE_INDEX);
        View view = inflater.inflate(R.layout.fragment_directions,container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.directionsLayout);

        String[] directions = Recipes.directions[index].split("`");

        //initialize check box array
        mCheckBoxes = new CheckBox[directions.length];
        boolean[] checkedBoxes = new boolean[mCheckBoxes.length];

        //make sure saved data exists before trying to access it:

        if(savedInstanceState != null && savedInstanceState.getBooleanArray(KEY_CHECKED_BOXES) != null){
            checkedBoxes =  savedInstanceState.getBooleanArray(KEY_CHECKED_BOXES);
        }

        setUpCheckBoxes(directions,linearLayout,checkedBoxes);

        return view;
    }

    public void setUpCheckBoxes(String[] directions, ViewGroup container, boolean[] checkedBoxes){

        int i = 0;

        //create for each loop to loop over each ingredient

        for(String direction : directions){
            //create checkbox for each ingredient.
            mCheckBoxes[i] = new CheckBox(getActivity());
            //make it pretty
            mCheckBoxes[i].setPadding(8,16,6,16);
            mCheckBoxes[i].setTextSize(20f);
            //set the text
            mCheckBoxes[i].setText(direction);
            //add it to layout
            container.addView(mCheckBoxes[i]);

            if(checkedBoxes[i]){
                mCheckBoxes[i].toggle();
            }

            i++;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        boolean[] stateOfCheckBoxes = new boolean[mCheckBoxes.length];
        int i = 0;
        for(CheckBox checkBox : mCheckBoxes){
            stateOfCheckBoxes[i] = checkBox.isChecked();
            i++;
        }

        outState.putBooleanArray(KEY_CHECKED_BOXES, stateOfCheckBoxes);
        super.onSaveInstanceState(outState);
        }
    }


