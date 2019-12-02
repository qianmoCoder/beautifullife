package com.ddu.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * Created by yzbzz on 2018/1/17.
 */
@Entity(tableName = "study_content")
class StudyContent(
        @NotNull
        @PrimaryKey
        var id: Long = 0,
        var title: String? = null,
        var description: String? = null,
        var type: String? = null,
        var isOld: Boolean = false)