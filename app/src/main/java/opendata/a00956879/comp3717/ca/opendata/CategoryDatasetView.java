package opendata.a00956879.comp3717.ca.opendata;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import opendata.a00956879.comp3717.ca.opendata.database.DatabaseHelper;
import opendata.a00956879.comp3717.ca.opendata.database.schema.Category;
import opendata.a00956879.comp3717.ca.opendata.database.schema.Dataset;
import opendata.a00956879.comp3717.ca.opendata.database.schema.DatasetDao;

public class CategoryDatasetView extends ListActivity {
    private DatasetDao datasetDao;
    private List<Dataset> datasetList;
    private long category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        Intent extras = getIntent();
        if (extras != null) {
            category_id = extras.getLongExtra("category_ref", 1L);
            setTitle(getCategoryName(category_id));
            datasetList = generateDatasetsList(category_id);
        }
        ListView list = (ListView) findViewById(android.R.id.list);
        ArrayAdapter<Dataset> adapter = new ArrayAdapter<Dataset>(this, android.R.layout.simple_list_item_1, datasetList);
        list.setAdapter(adapter);
    }

    private String getCategoryName(long category_id) {
        final String categoryName;
        final DatabaseHelper helper;
        helper = DatabaseHelper.getInstance(this);
        helper.openDatabaseForReading(this);
        categoryName = helper.getCategoryByCategoryId(category_id).toString();
        helper.close();
        return categoryName;
    }

    private List<Dataset> generateDatasetsList(long category_id) {
        final List<Dataset> datasetList;
        final DatabaseHelper helper;
        helper = DatabaseHelper.getInstance(this);
        helper.openDatabaseForReading(this);
        datasetList = helper.getDatasetByCategoryRef(category_id);
        helper.close();
        return datasetList;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Dataset item = (Dataset) l.getItemAtPosition(position);
        Intent i = new Intent(getApplicationContext(), DatasetView.class);
        i.putExtra("name", item.getName());
        i.putExtra("description", item.getDescription());
        startActivity(i);
    }
}
