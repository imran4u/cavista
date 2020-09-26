package com.imran.cavista.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imran.cavista.db.table.CommentModel
import com.imran.cavista.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by imran on 2020-09-26.
 */
class ImageDetailsViewModel(
    private val repository: CommentRepository
) : ViewModel() {
    var errorLiveDat = MutableLiveData<String>()
    val commentsLiveData = MutableLiveData<List<CommentModel>>()

    suspend fun insertComment(commentModel: CommentModel) = withContext(Dispatchers.Main) {
        try {
            repository.insertComment(commentModel)
        } catch (exp: Exception) {
            errorLiveDat.postValue("Exception to insert: ${exp.message}")
        }
    }

    suspend fun getComents(imageId: String) = withContext(Dispatchers.Main) {
        try {
            val comments = repository.getComments(imageId)
            commentsLiveData.postValue(comments)
        } catch (exp: Exception) {
            errorLiveDat.postValue("Exception to get comments: ${exp.message}")
        }
    }

    suspend fun getNextPage() {
        getImages(++pageNumber, queryText)
    }

}
