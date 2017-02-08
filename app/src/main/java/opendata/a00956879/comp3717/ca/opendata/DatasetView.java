package opendata.a00956879.comp3717.ca.opendata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class DatasetView extends AppCompatActivity {
    private String name;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataset_view);
        if(!readExtras()){
            setError();
        }
    }

    private void setError() {
        TextView nameHeader, descHeader;
        nameHeader = (TextView) findViewById(R.id.headername);
        descHeader = (TextView) findViewById(R.id.headerdescription);

        nameHeader.setText("An error has occured");
        descHeader.setText(null);
    }

    private boolean readExtras() {
        Intent extras = getIntent();
        if (extras != null) {
            name = extras.getStringExtra("name");
            setName(name);
            description = extras.getStringExtra("description");
            setDescription(description);
            return true;
        }
        return false;
    }

    private void setName(String name) {
        TextView nameView;
        setTitle(name);
        nameView = (TextView) findViewById(R.id.name);
        nameView.setText(name);
    }

    private void setDescription(String description){
        TextView descView;

        descView = (TextView) findViewById(R.id.description);
        descView.setText(description);
        initScrolling(descView);
    }

    private void initScrolling(TextView descView) {
        descView.setMovementMethod(new ScrollingMovementMethod());
    }
}
