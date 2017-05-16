package com.ddu.db;


import com.ddu.app.App;
import com.ddu.db.gen.DaoSession;
import com.ddu.db.gen.StudyContentDao;

/**
 * Created by yzbzz on 16/4/7.
 */
public class DbManager {

    private static DaoSession daoSession;

    static {
        daoSession = App.getDaoSession();
    }

    public static StudyContentDao getStudyContentDao() {
        return daoSession.getStudyContentDao();
    }
}
