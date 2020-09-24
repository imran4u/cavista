package com.imran.cavista.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imran.cavista.repository.ImageListRepository
import com.imran.cavista.viewmodel.ImageListViewModel

/**
 * Created by imran on 2020-09-24.
 */
class ImageListViewModelFactory(
    private val repository: ImageListRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageListViewModel(repository) as T
    }

}
