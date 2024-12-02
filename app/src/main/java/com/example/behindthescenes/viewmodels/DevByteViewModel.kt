package com.example.behindthescenes.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.behindthescenes.database.getDatabase
import com.example.behindthescenes.domain.Video
import com.example.behindthescenes.network.Network
import com.example.behindthescenes.network.asDomainModel
import com.example.behindthescenes.repository.VideosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

class DevByteViewModel(application: Application) : AndroidViewModel(application) {

   private val database = getDatabase(application)
    private val videosRepository = VideosRepository(database)

    fun initViewModel(isInternetAvailable: Boolean) {

        if (isInternetAvailable){
            viewModelScope.launch {
                videosRepository.refreshVideos()
            }
        } else{

        }

    }

    val playlist = videosRepository.videos



    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}