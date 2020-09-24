package com.imran.cavista.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imran.cavista.model.ImageWrapper
import com.imran.cavista.repository.ImageListRepository
import com.imran.cavista.util.ApiException
import com.imran.cavista.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by imran on 2020-09-24.
 */
class ImageListViewModel(
    private val repository: ImageListRepository
) : ViewModel() {
    var errorLiveDat = MutableLiveData<String>()
    val imageListLiveData = MutableLiveData<List<ImageWrapper>>()
    val imageList = mutableListOf<ImageWrapper>()
    var pageNumber: Int = 0

    suspend fun getImages(page: Int, query: String? = null) = withContext(Dispatchers.Main) {
        try {
            pageNumber = page
            val imagesResponse = repository.getImages(page, query)
            imageListLiveData.postValue(imagesResponse.data)
            if (page == 1) imageList.clear()
            imageList.addAll(imagesResponse.data)
        } catch (exp: ApiException) {
            errorLiveDat.postValue("Error: ${exp.message} - [ ${exp.code}]")
        } catch (exp: NoInternetException) {
            errorLiveDat.postValue(exp.message)
        }
    }

}
