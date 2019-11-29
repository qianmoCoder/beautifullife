package com.ddu.db;


import com.ddu.app.App;
import com.ddu.db.dao.StudyContentDao;

/**
 * Created by yzbzz on 16/4/7.
 */
public class DbManager {

    public static StudyContentDao getStudyContentDao() {
        return AppDatabase.Companion.getInstance(App.mContext).studyContentDao();
    }
}
