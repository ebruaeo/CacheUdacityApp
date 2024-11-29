package com.example.behindthescenes.repository

import com.example.behindthescenes.database.VideosDatabase
import com.example.behindthescenes.network.Network
import com.example.behindthescenes.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(private val database: VideosDatabase) {

    suspend fun refreshVideos(){
        withContext(Dispatchers.IO) {
            val playlist= Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}