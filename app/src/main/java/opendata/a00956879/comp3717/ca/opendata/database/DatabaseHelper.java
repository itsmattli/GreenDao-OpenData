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

    public List<Dataset> getDatasetByCategoryRef(final long category_ref) {
        final List<Dataset> datasets;

        datasets = datasetDao.queryBuilder().where(DatasetDao.Properties.Category_ref.eq(category_ref)).list();

        return datasets;
    }

    public Category getCategoryByCategoryId(final long category_id) {
        final List<Category> categories;
        final Category       category;

        categories = categoryDao.queryBuilder().where(CategoryDao.Properties.Category_id.eq(category_id)).limit(1).list();

        if(categories.isEmpty()) {
            category = null;
        } else {
            category = categories.get(0);
        }

        return (category);
    }

    public List<Category> getCategories() {
        return (categoryDao.loadAll());
    }

    public List<Dataset> getDatasets() {
        return (datasetDao.loadAll());
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
