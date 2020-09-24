package com.imran.cavista.repository

import com.imran.cavista.model.ImagesResponse
import com.imran.cavista.network.ApiRequestService
import com.imran.cavista.network.intercepter.SafeApiRequest

/**
 * Created by imran on 2020-09-24.
 */
class ImageListRepository(private val api: ApiRequestService) : SafeApiRequest() {

    suspend fun getImages(page: Int, query: String?): ImagesResponse {
        return apiRequest { api.getImages(page, query) }
    }
}
