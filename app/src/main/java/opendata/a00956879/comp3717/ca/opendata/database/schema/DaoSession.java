package opendata.a00956879.comp3717.ca.opendata.database.schema;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import opendata.a00956879.comp3717.ca.opendata.database.schema.Category;
import opendata.a00956879.comp3717.ca.opendata.database.schema.Dataset;

import opendata.a00956879.comp3717.ca.opendata.database.schema.CategoryDao;
import opendata.a00956879.comp3717.ca.opendata.database.schema.DatasetDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig categoryDaoConfig;
    private final DaoConfig datasetDaoConfig;

    private final CategoryDao categoryDao;
    private final DatasetDao datasetDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        categoryDaoConfig = daoConfigMap.get(CategoryDao.class).clone();
        categoryDaoConfig.initIdentityScope(type);

        datasetDaoConfig = daoConfigMap.get(DatasetDao.class).clone();
        datasetDaoConfig.initIdentityScope(type);

        categoryDao = new CategoryDao(categoryDaoConfig, this);
        datasetDao = new DatasetDao(datasetDaoConfig, this);

        registerDao(Category.class, categoryDao);
        registerDao(Dataset.class, datasetDao);
    }
    
    public void clear() {
        categoryDaoConfig.clearIdentityScope();
        datasetDaoConfig.clearIdentityScope();
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public DatasetDao getDatasetDao() {
        return datasetDao;
    }

}
