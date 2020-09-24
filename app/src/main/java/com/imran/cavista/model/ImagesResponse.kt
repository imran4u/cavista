package com.imran.cavista.model

/**
 * Created by imran on 2020-09-24.
 */

//TODO: work here
data class ImagesResponse(
    val success: Boolean,
    val status: Int,
    val data: List<ImageWrapper>
)
