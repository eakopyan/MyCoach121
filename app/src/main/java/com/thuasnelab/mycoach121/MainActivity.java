package com.thuasnelab.mycoach121;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MainFragment.OnFragmentInteractionListener,
        SportFragment.OnSessionClickListener,
        SessionFragment.OnButtonClickListener {

    public final static String PARENT_SPORT = "com.thuasnelab.mycoach121.PARENT_SPORT";
    public final static String SESSION = "com.thuasnelab.mycoach121.SESSION";

    private final static int REQ_SESSION = 0;

    MainFragment mFragment;
    SportFragment sFragment;
    SessionFragment detail;

    /**********************************************************************************************
     *
     *                                 MAIN ACTIVITY DECLARATION
     *
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Widgets par défaut
        setSupportActionBar(toolbar);

        mFragment = new MainFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**********************************************************************************************
     *
     *                                 MAIN ACTIVITY LISTENERS
     *
     *********************************************************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                Intent profile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profile);
                return true;
            case R.id.action_filter:
                return true;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof MainFragment) {
            mFragment = (MainFragment) fragment;
            mFragment.setOnFragmentInteractionListener(this);
        }
        if (fragment instanceof SportFragment) {
            sFragment = (SportFragment) fragment;
            sFragment.setOnSessionClickListener(this);
        }
        if (fragment instanceof SessionFragment) {
            detail = (SessionFragment) fragment;
        }
    }

    @Override
    public void onSportClick(Sport sport) {
        sFragment = SportFragment.newInstance(sport);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, sFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSessionClick(Session session) {
        detail = SessionFragment.newInstance(session);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detail)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddSession(String sportTitle) {
        Intent addSession = new Intent(MainActivity.this, AddSessionActivity.class);
        addSession.putExtra(PARENT_SPORT, sportTitle);
        startActivityForResult(addSession, REQ_SESSION);
    }

    // TODO: [DATABASE] implémenter les méthodes de modif et de suppression de séance
    @Override
    public void onButtonClick(int buttonId) {
        String which;
        if(buttonId == R.id.button_mod) which = "Modify";
        else which = "Delete";

        Toast.makeText(MainActivity.this,
                "Button "+which+" clicked!",
                Toast.LENGTH_SHORT)
                .show();
    }


    // TODO: [DATABASE] implémenter la méthode d'ajout de séance
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SESSION) {
            if (resultCode == RESULT_OK) {
                Session newSession = data.getParcelableExtra(MainActivity.SESSION);
                Toast.makeText(MainActivity.this,
                        "Session received! FC is "+newSession.getFc()+" bpm",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
