package com.generator.greendao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;
import org.greenrobot.greendao.generator.Property;

public class MainGenerator
{
    public static void main(String[] args)
            throws Exception
    {
        final Schema       schema;
        final DaoGenerator generator;

        schema = new Schema(1, "opendata.a00956879.comp3717.ca.opendata.database.schema");
        schema.enableKeepSectionsByDefault();
        addTables(schema);
        generator = new DaoGenerator();
        generator.generateAll(schema, "./app/src/main/java");
    }

    private static void addTables(final Schema schema) {
        Entity category = addCategories(schema);
        Entity dataset = addDatasets(schema);

        Property catID = dataset.addLongProperty("category_ref").notNull().getProperty();
        category.addToMany(dataset, catID);
    }

    private static Entity addCategories(final Schema schema) {
        Entity categories = schema.addEntity("Category");
        categories.addLongProperty("category_id").primaryKey().notNull();
        categories.addStringProperty("name").notNull();

        return categories;
    }

    private static Entity addDatasets(final Schema schema) {
        Entity datasets = schema.addEntity("Dataset");
        datasets.addIdProperty().primaryKey().autoincrement();
        datasets.addStringProperty("name").notNull();
        datasets.addStringProperty("description");

        return datasets;
    }


}
