package com.ominfo.crm_solution.database;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DBMigration {


    public static final Migration MIGRATION_2_3 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            /*database.execSQL("ALTER TABLE virtual_card "
                    + " ADD COLUMN custId TEXT");*/
        }
    };

}
