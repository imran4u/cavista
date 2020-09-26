package com.imran.cavista.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imran.cavista.db.table.CommentModel
import com.imran.cavista.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.withContext
import java.util.concurrent.CompletableFuture

/**
 * Created by imran on 2020-09-26.
 */
class ImageDetailsViewModel(
    private val repository: CommentRepository
) : ViewModel() {
    val errorLiveDat = MutableLiveData<String>()
    val commentsLiveData = MutableLiveData<List<CommentModel>>()
    val insertResultLiveData = MutableLiveData<Boolean>()

    suspend fun insertComment(commentMessage: String, imageId: String) =
        withContext(Dispatchers.Main) {
            try {
                repository.insertComment(CommentModel(comment = commentMessage, imageId = imageId))
                insertResultLiveData.postValue(true)
            } catch (exp: Exception) {
                errorLiveDat.postValue("Exception to insert: ${exp.message}")
                insertResultLiveData.postValue(false)
            }
        }

    suspend fun getComments(imageId: String) = withContext(Dispatchers.Main) {
        try {
            val comments = repository.getComments(imageId)
            commentsLiveData.postValue(comments)
        } catch (exp: Exception) {
            errorLiveDat.postValue("Exception to get comments: ${exp.message}")
        }
    }

    fun insertCommentAsy(commentMessage: String, imageId: String): CompletableFuture<Any> =
        GlobalScope.future { insertComment(commentMessage, imageId) }

    fun getCommentAsync(imageId: String): CompletableFuture<Any> =
        GlobalScope.future { getComments(imageId) }

}
