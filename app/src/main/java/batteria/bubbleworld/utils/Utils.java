package batteria.bubbleworld.utils;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import batteria.bubbleworld.database.dao.DaoMaster;
import batteria.bubbleworld.database.dao.DaoSession;

public class Utils extends Application {
    private static Context context;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "test.db");
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();

    }

    public static Context getContext() {
        return context;
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }
}
