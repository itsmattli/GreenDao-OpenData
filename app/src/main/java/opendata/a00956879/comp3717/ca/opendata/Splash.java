package opendata.a00956879.comp3717.ca.opendata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import opendata.a00956879.comp3717.ca.opendata.CategoryView;
import opendata.a00956879.comp3717.ca.opendata.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTitle("New West Datasets");
    }

    public void onClickSplash(View v) {
        Intent i = new Intent(getApplicationContext(), CategoryView.class);
        startActivity(i);
    }
}
