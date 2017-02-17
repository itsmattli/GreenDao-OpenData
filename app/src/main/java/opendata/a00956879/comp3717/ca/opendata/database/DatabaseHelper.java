package opendata.a00956879.comp3717.ca.opendata.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.greenrobot.greendao.database.Database;
import java.util.List;
import opendata.a00956879.comp3717.ca.opendata.database.schema.Category;
import opendata.a00956879.comp3717.ca.opendata.database.schema.CategoryDao;
import opendata.a00956879.comp3717.ca.opendata.database.schema.DaoMaster;
import opendata.a00956879.comp3717.ca.opendata.database.schema.DaoSession;
import opendata.a00956879.comp3717.ca.opendata.database.schema.Dataset;
import opendata.a00956879.comp3717.ca.opendata.database.schema.DatasetDao;

/**
 * Created by Matthew on 2017-02-06.
 */

public class DatabaseHelper
{
    private final static String TAG = DatabaseHelper.class.getName();
    private static DatabaseHelper          instance;
    private        SQLiteDatabase          db;
    private        DaoMaster               daoMaster;
    private        DaoSession              daoSession;
    private        CategoryDao             categoryDao;
    private        DatasetDao              datasetDao;
    private        DaoMaster.DevOpenHelper helper;

    private DatabaseHelper(final Context context) {
        openDatabaseForWriting(context);
    }

    public synchronized static DatabaseHelper getInstance(final Context context) {
        if(instance == null) {
            instance = new DatabaseHelper(context);
        }
        return (instance);
    }

    public static DatabaseHelper getInstance() {
        if(instance == null) {
            throw new Error();
        }
        return (instance);
    }

    private void openDatabase() {
        daoMaster      = new DaoMaster(db);
        daoSession     = daoMaster.newSession();
        datasetDao     = daoSession.getDatasetDao();
        categoryDao    = daoSession.getCategoryDao();
    }

    public void openDatabaseForWriting(final Context context) {
        helper = new DaoMaster.DevOpenHelper(context,
                "datasets.db",
                null);
        db = helper.getWritableDatabase();
        openDatabase();
    }

    public void openDatabaseForReading(final Context context) {
        final DaoMaster.DevOpenHelper helper;

        helper = new DaoMaster.DevOpenHelper(context,
                "datasets.db",
                null);
        db = helper.getReadableDatabase();
        openDatabase();
    }

    public void close() {
        helper.close();
    }


    public Dataset createDataset(final String name, final String description, long category_ref) {
        final Dataset dataset;

        dataset = new Dataset(null, name, description, category_ref);
        datasetDao.insert(dataset);

        return (dataset);
    }

    public Category createCategory(long category_id, final String name) {
        final Category category;

        category = new Category(category_id, name);
        categoryDao.insert(category);

        return (category);
    }

    public Cursor getCategoryCursor() {
        final Cursor cursor;

        String orderBy = CategoryDao.Properties.Name.columnName + " ASC";
        cursor = db.query(categoryDao.getTablename(),
                categoryDao.getAllColumns(),
                null,
                null,
                null,
                null,
                orderBy);

        return (cursor);
    }

    public Cursor getCategoryDatasetCursor(String selection) {
        final Cursor cursor;

        String orderBy = DatasetDao.Properties.Name.columnName + " ASC";
        cursor = db.query(datasetDao.getTablename(),
                datasetDao.getAllColumns(),
                selection,
                null,
                null,
                null,
                orderBy);

        return (cursor);
    }

    public Cursor getDatasetCursor(String selection) {
        final Cursor cursor;

        String orderBy = CategoryDao.Properties.Name.columnName + " ASC";
        cursor = db.query(datasetDao.getTablename(),
                datasetDao.getAllColumns(),
                selection,
                null,
                null,
                null,
                orderBy);

        return (cursor);
    }

    public Dataset getDatasetFromCursor(Cursor cursor) {
        final Dataset dataset;

        dataset = datasetDao.readEntity(cursor,
                0);

        return dataset;
    }

    public Category getCategoryFromCursor(final Cursor cursor) {
        final Category category;

        category = categoryDao.readEntity(cursor,
                0);

        return (category);
    }

    public static void upgrade(final Database db,
                               final int      oldVersion,
                               final int      newVersion) {
    }

    public long getNumberOfCategories() {
        return (categoryDao.count());
    }

    public long getNumberOfDatasets() {
        return (datasetDao.count());
    }
}
