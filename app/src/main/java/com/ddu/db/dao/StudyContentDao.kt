package com.ddu.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ddu.db.entity.StudyContent

/**
 * Created by yzbzz on 2019/11/29.
 */
@Dao
interface StudyContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(studyContent: List<StudyContent>)

    @Query("select * from study_content")
    fun getStudyContents(): List<StudyContent>
}