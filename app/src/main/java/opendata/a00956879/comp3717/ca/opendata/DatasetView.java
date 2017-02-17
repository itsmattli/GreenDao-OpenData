package opendata.a00956879.comp3717.ca.opendata;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import opendata.a00956879.comp3717.ca.opendata.database.DatabaseHelper;
import opendata.a00956879.comp3717.ca.opendata.database.schema.Dataset;

public class DatasetView extends AppCompatActivity {
    public static final String DATASET_PATH = "/dataset/";
    private long dataset_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LoaderManager manager;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataset_view);
        if(!readExtras()){
            setError();
        } else {
            manager = getLoaderManager();
            manager.initLoader(0, null, new DatasetView.DatasetLoaderCallbacks());
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
            dataset_id = extras.getLongExtra("id", 1L);
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

    private Dataset parseCursor(final Cursor cursor) {
        final DatabaseHelper helper;

        helper = DatabaseHelper.getInstance(this);
        return helper.getDatasetFromCursor(cursor);
    }

    private void initScrolling(TextView descView) {
        descView.setMovementMethod(new ScrollingMovementMethod());
    }

    private class DatasetLoaderCallbacks
            implements LoaderManager.LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(final int id,
                                             final Bundle args) {
            final Uri uri;
            final CursorLoader loader;

            uri = Uri.parse(DataContentProvider.CONTENT_URI.toString() + DATASET_PATH + dataset_id);

            loader = new CursorLoader(DatasetView.this, uri, null, "_id =" + dataset_id, null, null);

            return (loader);
        }

        @Override
        public void onLoadFinished(final Loader<Cursor> loader,
                                   final Cursor data) {
            if (data.moveToFirst()) {
                Dataset dataset = parseCursor(data);
                setName(dataset.getName());
                setDescription(dataset.getDescription());
            } else {
                setError();
            }
        }

        @Override
        public void onLoaderReset(final Loader<Cursor> loader) {
        }
    }
}
