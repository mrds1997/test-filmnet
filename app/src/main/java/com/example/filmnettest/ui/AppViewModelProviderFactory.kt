package com.colbeh.virtualphone.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmnettest.data.network.Repository
import com.example.filmnettest.ui.AppViewModel

class AppViewModelProviderFactory(private val repository: Repository) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AppViewModel(repository) as T
    }
}