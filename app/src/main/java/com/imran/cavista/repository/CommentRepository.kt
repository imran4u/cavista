package com.imran.cavista.repository

import com.imran.cavista.db.CommentDatabase
import com.imran.cavista.db.table.CommentModel

/**
 * Created by imran on 2020-09-26.
 */

class CommentRepository(private val database: CommentDatabase) {
    suspend fun insertComment(comment: CommentModel) {
        return database.comentDao().insertComment(comment)
    }

    suspend fun getComments(imageId: String): List<CommentModel> {
        return database.comentDao().getCommentList(imageId)
    }

}
