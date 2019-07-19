package com.thuasnelab.mycoach121;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddSessionActivity extends AppCompatActivity {

    private EditText sessionTitle;
    private EditText sessionDate;
    private EditText sessionDistance;
    private EditText sessionFC;
    private EditText sessionDuree;

    private String sportTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent parent = getIntent();
        String parentSport = parent.getStringExtra(MainActivity.PARENT_SPORT);
        switch(parentSport) {
            case "roller_hockey":
                sportTitle = "roller hockey";
                break;
            case "velo_route":
                sportTitle = "vélo sur route";
                break;
            case "roller_vitesse":
                sportTitle = "roller de vitesse";
                break;
            case "danse":
                sportTitle = "danse hip-hop";
                break;
            default: sportTitle = ""; break;
        }
        TextView title = findViewById(R.id.addsession_sport);
        title.setText(title.getText()+sportTitle);

        sessionTitle = findViewById(R.id.addsession_title);
        sessionDate = findViewById(R.id.addsession_date);
        sessionDistance = findViewById(R.id.addsession_distance);
        sessionFC = findViewById(R.id.addsession_FC);
        sessionDuree = findViewById(R.id.addsession_duree);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session session = new Session(
                        sessionDate.getText().toString(),
                        sportTitle,
                        sessionTitle.getText().toString(),
                        Double.parseDouble(sessionDistance.getText().toString()),
                        Integer.parseInt(sessionFC.getText().toString()),
                        sessionDuree.getText().toString());

                Intent result = new Intent();
                result.putExtra(MainActivity.SESSION, session);
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }

}
