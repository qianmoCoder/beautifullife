package com.ddu.db;


import com.ddu.app.App;
import com.ddu.db.entity.StudyContent;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by yzbzz on 16/4/7.
 */
public class DbManager {

    private static BoxStore boxStore;

    static {
        boxStore = App.Companion.getBoxStore();
    }

    public static Box<StudyContent> getStudyContentBox() {
        return boxStore.boxFor(StudyContent.class);
    }
}
