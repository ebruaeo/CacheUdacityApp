package com.example.behindthescenes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.behindthescenes.database.VideosDatabase
import com.example.behindthescenes.database.asDomainModel
import com.example.behindthescenes.domain.Video
import com.example.behindthescenes.network.Network
import com.example.behindthescenes.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(private val database: VideosDatabase) {

    val videos: LiveData<List<Video>> =
        database.videoDao.getVideos().map {
            it.asDomainModel()
        }

    suspend fun refreshVideos(){
        withContext(Dispatchers.IO) {
            val playlist= Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}