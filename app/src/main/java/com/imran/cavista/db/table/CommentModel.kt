package com.imran.cavista.db.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by imran on 2020-09-26.
 */

@Entity(tableName = "comment_table")
class CommentModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "image_id") val imageId: String
)
