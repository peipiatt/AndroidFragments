package com.example.piatt.smellslikebakin;

/**
 * Created by piatt on 8/29/2016.
 */



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ViewPagerFragment extends Fragment {


    public static final String KEY_RECIPE_INDEX = "recipe_index";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int index = getArguments().getInt(KEY_RECIPE_INDEX);


        //allows us to set the title of the activity.

        getActivity().setTitle(Recipes.names[index]);

        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);

        final IngredientsFragment ingredientsFragment = new IngredientsFragment();

        //we do this to pass the tuple to our ingredients fragment from this fragment.
        //this fragment is calling the ingredients fragment
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_RECIPE_INDEX, index);
        ingredientsFragment.setArguments(bundle);


        final DirectionsFragment directionsFragment = new DirectionsFragment();

        bundle = new Bundle();
        bundle.putInt(KEY_RECIPE_INDEX, index);
        directionsFragment.setArguments(bundle);


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        //when dealing with fragments within fragments use the
        //childfragmetnmanager
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
                                 @Override
                                 public int getCount() {
                                     return 2;
                                 }

            @Override
            public CharSequence getPageTitle(int position) {
                if(position == 0){
                    return "Ingredients";
                }else{
                    return "Directions";
                }

            }

            @Override
                                 public Fragment getItem(int position) {
                                     if(position == 0){
                                         return ingredientsFragment;
                                     }else {
                                         return directionsFragment;
                                     }
                                 }
                             }
        );

        //This is all we need to do to hook up tab layout to view pager.

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        //this retrieves that app name string from the xml file
        //On stopping it sets the title to the name of the recipe.

        getActivity().setTitle(getResources().getString(R.string.app_name));
    }
}
