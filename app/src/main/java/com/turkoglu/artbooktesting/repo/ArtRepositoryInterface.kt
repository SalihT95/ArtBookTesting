package com.turkoglu.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.bumptech.glide.load.engine.Resource
import com.turkoglu.artbooktesting.model.ImageResponse
import com.turkoglu.artbooktesting.roomdb.Art

interface ArtRepositoryInterface {

    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : com.turkoglu.artbooktesting.Util.Resource<ImageResponse>

}