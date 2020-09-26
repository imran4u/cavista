package com.imran.cavista.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imran.cavista.db.table.CommentModel

/**
 * Created by imran on 2020-09-26.
 */
@Dao
interface DAOComment {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentModel)

    @Query("SELECT * FROM comment_table WHERE image_id =:imageId")
    suspend fun getCommentList(imageId: String?) : List<CommentModel>

}
