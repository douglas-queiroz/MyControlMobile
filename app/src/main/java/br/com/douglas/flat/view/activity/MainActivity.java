package br.com.douglas.flat.view.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import java.util.Stack;

import br.com.douglas.flat.view.fragments.ClientDetailFragment;
import br.com.douglas.flat.view.fragments.ClientFragment;
import br.com.douglas.flat.view.fragments.NavigationDrawerFragment;
import br.com.douglas.flat.R;
import br.com.douglas.flat.view.fragments.SaleItemFragment;
import br.com.douglas.flat.view.fragments.StartFragment;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        ClientFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private Stack<Fragment> stackFragment = new Stack<Fragment>();
    private Fragment currentyFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        stackFragment.clear();
        Bundle args = new Bundle();
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new StartFragment();
                args.putInt(StartFragment.ARG_SECTION_NUMBER, position + 1);
                fragment.setArguments(args);
                startFragment(fragment);
                break;
            case 1:
                fragment = new ClientFragment();
                args.putInt(ClientFragment.ARG_SECTION_NUMBER, position + 1);
                fragment.setArguments(args);
                startFragment(fragment);
                break;
            case 2:
                fragment = new SaleItemFragment();
                args.putInt(SaleItemFragment.ARG_SECTION_NUMBER, position + 1);
                fragment.setArguments(args);
                startFragment(fragment);
                break;

        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_start);
                break;
            case 2:
                mTitle = getString(R.string.title_client);
                break;
            case 3:
                mTitle = getString(R.string.title_sales);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            if(currentyFragment  != null){
                currentyFragment.onCreateOptionsMenu(menu, getMenuInflater());
            }else {
                getMenuInflater().inflate(R.menu.main, menu);
            }

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(currentyFragment != null){
            return currentyFragment.onOptionsItemSelected(item);
        }else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onBackPressed() {
        if(stackFragment.empty()){
            super.onBackPressed();
        }else{
            stackFragment.pop();
            if(!stackFragment.isEmpty()) {
                currentyFragment = stackFragment.peek();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, currentyFragment)
                        .commit();

                invalidateOptionsMenu();
            }else{
                super.onBackPressed();
            }
        }
    }

    public void startFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        invalidateOptionsMenu();
        currentyFragment = fragment;
        stackFragment.add(fragment);
    }

    @Override
    protected void onResume() {
        if (currentyFragment != null){
            currentyFragment.onResume();
            invalidateOptionsMenu();
        }
        super.onResume();
    }
}
