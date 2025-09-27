package com.example.phonebookapplication.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewModel(val repository: UserRepository): ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun fetchAllUsers() {
        viewModelScope.launch {
            try {
                _users.value = repository.getAllUsers()
            }
            catch (ex: Exception) {
                Log.e("HomeViewModel", "fetchAllUsers: ", ex)
            }

        }
    }
}