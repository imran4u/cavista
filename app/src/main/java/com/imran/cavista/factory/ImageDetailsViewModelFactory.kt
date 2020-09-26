package com.imran.cavista.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imran.cavista.repository.CommentRepository
import com.imran.cavista.viewmodel.ImageDetailsViewModel

/**
 * Created by imran on 2020-09-26.
 */
class ImageDetailsViewModelFactory(
    private val repository: CommentRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageDetailsViewModel(repository) as T
    }

}
