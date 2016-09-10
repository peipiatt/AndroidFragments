package com.example.piatt.smellslikebakin;



//The support versions are here because we're using support fragments
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListFragment.OnRecipeSelectedInterface{

    //we have to add these as hard references to the fragments. without them
    //we can lose the reference to the fragment.

    public static final String LIST_FRAGMENT = "list_fragment";
    //we can use find fragment by tag instead of id. Id can be lost.
    //This constant final variable can't
    public static final String VIEWPAGER_FRAGMENT = "viewpager_fragment";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //if this fragment can't be found it will return null. This is a check to stop multiple
        //fragments from being created from screen rotations.
        ListFragment savedFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);
        //could also use savedInstanceState
        if( savedFragment == null) {
            //make fragment of type ListFragment
            ListFragment fragment = new ListFragment();
            //Then make manager to manage said fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Now fragment transaction
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //add placeholder and fragment to fragment transaction
            fragmentTransaction.add(R.id.placeHolder, fragment, LIST_FRAGMENT);
            //do it.
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onListRecipeSelected(int index) {
        Toast.makeText(MainActivity.this, Recipes.names[index], Toast.LENGTH_SHORT).show();

        //make fragment of type ListFragment
        ViewPagerFragment fragment = new ViewPagerFragment();

        //Build a bundle:
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
        fragment.setArguments(bundle);

        //Then make manager to manage said fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Now fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //add placeholder and fragment to fragment transaction
        fragmentTransaction.replace(R.id.placeHolder, fragment, VIEWPAGER_FRAGMENT);

        //add to back stack:
        //we're only going back one at a time so we pass in null.

        /*
            *!!!!! This will end the app oh no. So we have to fix that.
            * Regular fragments should be paired with the activity class.
            * support fragments should be paired with the app compact activity class
         */

        fragmentTransaction.addToBackStack(null);

        //do it.
        fragmentTransaction.commit();

    }
}
