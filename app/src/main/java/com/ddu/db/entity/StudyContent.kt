package com.ddu.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by yzbzz on 2018/1/17.
 */
@Entity(tableName = "study_content")
class StudyContent {
    @PrimaryKey
    var id: Long = 0
    var title: String? = null
    var description: String? = null
    var type: String? = null
    var isOld = false

}