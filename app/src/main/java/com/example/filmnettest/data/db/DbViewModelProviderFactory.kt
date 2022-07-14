package com.example.filmnettest.data.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DbViewModelProviderFactory(private val repository: DBRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DbViewModel(repository) as T
    }
}