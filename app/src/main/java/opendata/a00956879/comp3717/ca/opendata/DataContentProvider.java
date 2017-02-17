package opendata.a00956879.comp3717.ca.opendata;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import opendata.a00956879.comp3717.ca.opendata.database.DatabaseHelper;

public class DataContentProvider
        extends ContentProvider
{
    private static final UriMatcher uriMatcher;
    private static final int CATEGORY_URI = 1;
    private static final int CATEGORY_DATASET_URI = 2;
    private static final int DATASET_ID_URI = 3;
    public static final Uri CONTENT_URI;
    private DatabaseHelper helper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("opendata.a00956879.comp3717.ca.opendata", "datasets/category", CATEGORY_URI);
        uriMatcher.addURI("opendata.a00956879.comp3717.ca.opendata", "datasets/category/#", CATEGORY_DATASET_URI);
        uriMatcher.addURI("opendata.a00956879.comp3717.ca.opendata", "datasets/dataset/#", DATASET_ID_URI);
    }

    static {
        CONTENT_URI = Uri.parse("content://opendata.a00956879.comp3717.ca.opendata/datasets/");
    }

    @Override
    public boolean onCreate() {
        helper = DatabaseHelper.getInstance(getContext());

        return true;
    }

    @Override
    public Cursor query(final Uri uri,
                        final String[] projection,
                        final String selection,
                        final String[] selectionArgs,
                        final String sortOrder) {
        final Cursor cursor;

        switch (uriMatcher.match(uri))
        {
            case CATEGORY_URI:
            {
                final SQLiteDatabase db;

                helper.openDatabaseForReading(getContext());
                cursor = helper.getCategoryCursor();
                helper.close();
                break;
            }

            case CATEGORY_DATASET_URI:
            {
                final SQLiteDatabase db;

                helper.openDatabaseForReading(getContext());
                cursor = helper.getCategoryDatasetCursor(selection);
                helper.close();
                break;
            }

            case DATASET_ID_URI:
            {
                final SQLiteDatabase db;

                helper.openDatabaseForReading(getContext());
                cursor = helper.getDatasetCursor(selection);
                helper.close();
                break;
            }
            default:
            {
                throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        }

        return (cursor);
    }

    @Override
    public String getType(final Uri uri) {
        final String type;

        switch(uriMatcher.match(uri))
        {
            case CATEGORY_URI:
                type = "vnd.android.cursor.dir/vnd.opendata.a00956879.comp3717.ca.opendata.datasets";
                break;
            case CATEGORY_DATASET_URI:
                type = "vnd.android.cursor.dir/vnd.opendata.a00956879.comp3717.ca.opendata.datasets";
                break;
            case DATASET_ID_URI:
                type = "vnd.android.cursor.dir/vnd.opendata.a00956879.comp3717.ca.opendata.datasets";
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        return (type);
    }

    @Override
    public int delete(final Uri uri,
                      final String selection,
                      final String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(final Uri uri,
                      final ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(final Uri uri,
                      final ContentValues values,
                      final String selection,
                      final String[]      selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}