package opendata.a00956879.comp3717.ca.opendata;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import opendata.a00956879.comp3717.ca.opendata.database.DatabaseHelper;
import opendata.a00956879.comp3717.ca.opendata.database.schema.Dataset;
import opendata.a00956879.comp3717.ca.opendata.database.schema.DatasetDao;

public class CategoryDatasetView extends ListActivity {
    public static final String CATEGORY_DATASET_PATH = "/category/";
    private long category_id;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LoaderManager manager;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        Intent extras = getIntent();
        if (extras != null) {
            category_id = extras.getLongExtra("category_ref", 1L);
            adapter = new SimpleCursorAdapter(getBaseContext(),
                    android.R.layout.simple_list_item_1,
                    null,
                    new String[]
                            {
                                    DatasetDao.Properties.Name.columnName,
                            },
                    new int[]
                            {
                                    android.R.id.text1,
                            },
                    0);

            setListAdapter(adapter);
            manager = getLoaderManager();
            manager.initLoader(0, null, new CategoryDatasetView.CategoryDatasetLoaderCallbacks());
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Cursor item = (Cursor) l.getItemAtPosition(position);
        Dataset dataset = parseCursor(item);
        Intent i = new Intent(getApplicationContext(), DatasetView.class);
        i.putExtra("id", dataset.getId());
        startActivity(i);
    }

    private Dataset parseCursor(final Cursor cursor) {
        final DatabaseHelper helper;

        helper = DatabaseHelper.getInstance(this);
        return helper.getDatasetFromCursor(cursor);
    }

    private class CategoryDatasetLoaderCallbacks
            implements LoaderManager.LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(final int id,
                                             final Bundle args) {
            final Uri uri;
            final CursorLoader loader;

            uri = Uri.parse(DataContentProvider.CONTENT_URI.toString() + CATEGORY_DATASET_PATH + category_id);

            loader = new CursorLoader(CategoryDatasetView.this, uri, null, "category_ref =" + category_id, null, null);

            return (loader);
        }

        @Override
        public void onLoadFinished(final Loader<Cursor> loader,
                                   final Cursor data) {
            adapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(final Loader<Cursor> loader) {
            adapter.swapCursor(null);
        }
    }
}
